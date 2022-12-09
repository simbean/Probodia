import torch
import argparse
import wandb
import random
import pandas as pd
import os
import numpy as np
from .model import * 
from .optim import *
from .loss import *
from .matrix import *
from .scheduler import *
from tqdm import tqdm

from foodTrain.dataset import FoodDataset, setDataloader
import albumentations as A
import albumentations.pytorch 
# Same transform with torchvision_transform
from sklearn.metrics import f1_score
from sklearn.metrics import accuracy_score


def seed_everything(seed: int = 42):
    random.seed(seed)
    np.random.seed(seed)
    os.environ["PYTHONHASHSEED"] = str(seed)
    torch.manual_seed(seed)
    torch.cuda.manual_seed(seed)  # type: ignore
    torch.backends.cudnn.deterministic = True  # type: ignore
    torch.backends.cudnn.benchmark = True  # type: ignore


def train(gpu, ngpus_per_node, args : argparse.ArgumentParser):
    seed_everything()
    if gpu == 0:
        wandb.init(project = "soma", entity = 'simbean')
        wandb.config.update(args)
        wandb.run.name = f'{args.name}+{args.optimizer}+{args.lr}'
        wandb.run.save()
    torch.distributed.init_process_group(
        backend='nccl',
        init_method='tcp://localhost:3456',
        world_size=ngpus_per_node,
        rank=gpu)
    #augmentation
    albumentations_transform = A.Compose([
                                    A.Resize(args.img_size, args.img_size),
                                    A.HorizontalFlip(p = 0.5), # Same with transforms.RandomHorizontalFlip()
                                    A.OneOf([
                                                A.CLAHE(p = 1.0),
                                                A.Sharpen(p = 1.0),   
                                            ], p=0.5),
                                    A.RandomBrightnessContrast(brightness_limit=[0.1, 0.3],
                                                               contrast_limit=[0.1, 0.3],
                                                               p = 0.2),
                                    A.ImageCompression(quality_lower=85, quality_upper=95, p=0.2),
                                    A.OneOf([      
                                                A.GaussNoise(p = 1.0), 
                                                A.Blur(blur_limit=3, p = 1.0),
                                                A.MedianBlur(blur_limit=3, p=1.0)
                                            ], p=0.2),
                                    A.Normalize(),
                                    albumentations.pytorch.transforms.ToTensorV2()
                                ])


    #set dataloader
    dataloader = setDataloader(csv_file_list = ['/media/fireban/Passport/food_train_df.csv', '/media/fireban/Passport/food_valid_df.csv'], 
                               root_dir = './traincropimg', 
                               batch_size = args.batch_size,
                               img_size = args.img_size,
                               transform = albumentations_transform)
    #device = 'cuda' if torch.cuda.is_available() else 'cpu'


    #set model
    model = selectModel(name = args.model,
                        class_num = args.class_num,
                        device = gpu)
    
    torch.cuda.set_device(gpu)
    model = model.cuda(gpu)
    model = torch.nn.parallel.DistributedDataParallel(model, device_ids=[gpu])
    if args.load_weight != 'None':
        model.load_state_dict(torch.load(args.load_weight))
        print('model update')



    #set loss, optimizer, scheduler
    criterion = selectLoss(name = args.loss)
    optimizer = selectOptimizer(name = args.optimizer,
                                model = model,
                                param_lr = args.lr)

    scheduler = setScheduler(name = args.scheduler,
                             optimizer = optimizer)
    scaler = torch.cuda.amp.GradScaler()    #prev value init
    early_stop_flag = 0
    prev_val_loss = float('inf')

    for epoch in range(args.epoch):  # loop over the dataset multiple times
        if epoch == args.freeze:
            model.freeze()
        if epoch < args.restart:
            scheduler.step()
            continue
        #temp value init
        running_loss = 0.0
        running_val_loss = 0.0
        total = 0.0
        running_accuracy = 0.0
        rank_5_running_accuracy = 0.0
        rank_3_running_accuracy = 0.0
        best_F1_score = 0.0
        f1_arr = []
        acc_arr = []
        f1_weighted_arr = []
        class_acc_arr = [0 for i in range(args.class_num)]
        class_num_arr = [0 for i in range(args.class_num)]

        model.train()
        for i, (imgs, labels) in enumerate(tqdm(dataloader['train'])):
            # get the inputs; data is a list of [inputs, labels]
            inputs = imgs.to(gpu).float()
            targets = labels.to(gpu)
            # zero the parameter gradients
            optimizer.zero_grad()
            
            with torch.cuda.amp.autocast():
                outputs = model(inputs)
                loss = criterion(outputs, targets)
            # forward + backward + optimize
            scaler.scale(loss).backward()
            scaler.step(optimizer)
            scaler.update()
            # print statistics
            running_loss += loss.item()


        #valid
        with torch.no_grad(): 
            model.eval() 
            for i, (imgs, labels) in enumerate(tqdm(dataloader['valid'])):
                inputs = imgs.to(gpu).float()
                targets = labels.to(gpu)
                outputs = model(inputs)
                val_loss = criterion(outputs, targets)
                # The label with the highest value will be our prediction 
                _, predicted = torch.max(outputs, 1) 
                _, rank5_predicted = torch.sort(outputs, descending = True)
                running_val_loss += val_loss.item()


                total += len(targets)
                running_accuracy += torch.sum(predicted == targets)
                #acc@5, acc@3
                rank_5_running_accuracy = acc_rank(temp_sum = rank_5_running_accuracy, labels = targets, preds_arr = rank5_predicted, rank = 5)
                rank_3_running_accuracy = acc_rank(temp_sum = rank_3_running_accuracy, labels = targets, preds_arr = rank5_predicted, rank = 3)

                #f1 score, accuracy
                temp_batch_acc = accuracy_score(y_true = labels.numpy(), y_pred = predicted.cpu().numpy())
                temp_batch_F1_score = f1_score(y_true = labels.numpy(), y_pred = predicted.cpu().numpy(), average = "macro")
                temp_batch_weighted_F1_score = f1_score(y_true = labels.numpy(), y_pred = predicted.cpu().numpy(), average = "weighted")
                acc_arr.append(temp_batch_acc)
                f1_arr.append(temp_batch_F1_score)
                f1_weighted_arr.append(temp_batch_weighted_F1_score)

                #class - acc
                for true, pred in zip(targets, predicted):
                    class_num_arr[true] += 1
                    if true == pred:
                        class_acc_arr[true] += 1
        scheduler.step()
        # Calculate validation loss value 
        # Calculate accuracy as the number of correct predictions in the validation batch divided by the total number of predictions done.  


        accuracy = (100 * (running_accuracy / total))    
        rank_5_accuracy = (100 * (rank_5_running_accuracy / total))
        rank_3_accuracy = (100 * (rank_3_running_accuracy / total))
        temp_epoch_F1_score = np.mean(f1_arr)
        temp_epoch_acc_score = np.mean(acc_arr)
        temp_epoch_F1_weighted_score = np.mean(f1_weighted_arr)
        np_class_acc_arr = np.divide(class_acc_arr, class_num_arr)
        if gpu == 0:
            wandb.log({
                        "running_loss": running_loss / len(dataloader['train']),
                        "val_loss_value": running_val_loss/len(dataloader['valid']) ,
                        "accuracy": accuracy,
                        "rank5 acc" : rank_5_accuracy,
                        "rank3 acc" : rank_3_accuracy,
                        "acc_sklearn" : temp_epoch_acc_score,
                        "f1_score": temp_epoch_F1_score, 
                        "f1_weighted_score" : temp_epoch_F1_weighted_score,
                        "class_num_img" : class_num_arr,
                        "class_acc" : [i if 0<= i <= 1 else 0 for i in np_class_acc_arr]
                    })

        if temp_epoch_F1_score > best_F1_score: 
            best_F1_score = temp_epoch_F1_score 
            torch.save(model.state_dict(), f'weight/{args.name}_{gpu}.pt')
        torch.save(model.state_dict(), f'weight/model{epoch}_{gpu}.pt')
        val =  running_val_loss/len(dataloader['valid'])
        print(prev_val_loss, val)
        if prev_val_loss > val:
            early_stop_flag = 0
        else:
            early_stop_flag += 1
        prev_val_loss = val
        if early_stop_flag >= args.early_stop:
            print('early stop')
            break
        #print(f'f1 score : {temp_epoch_F1_score}, acc : {temp_epoch_acc_score}')
        print(f'Finished Training, early stop = {early_stop_flag} / {args.early_stop}')

