import torch
import argparse
import wandb
import random
import pandas as pd
import os
import numpy as np
import sys
from os import path
print(path.dirname( path.dirname( path.abspath(__file__) ) ))
sys.path.append(path.dirname( path.dirname( path.abspath(__file__) ) ))

from foodTrain.model import * 
from foodTrain.optim import *
from foodTrain.loss import *
from foodTrain.matrix import *
from foodTrain.scheduler import *
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

    
def inference():
    seed_everything()
    wandb.init(project = "soma", entity = 'simbean')

    wandb.run.save()
    device = 'cuda' if torch.cuda.is_available() else 'cpu'
    albumentations_transform = A.Compose([
                            A.Resize(300, 300),
                            A.Normalize(),
                            albumentations.pytorch.transforms.ToTensorV2()
                        ])
    model = selectModel(name = 'efficientNetV2',
                    class_num = 400,
                    device = device)
    model.load_state_dict(torch.load('model3.pt', map_location=device))
    #set dataloader
    dataloader = setDataloader(csv_file_list = ['train1_df.csv', 'valid2_df.csv'], 
                               root_dir = './traincropimg', 
                               batch_size = 1,
                               img_size = 300,
                               transform = albumentations_transform)
    criterion = selectLoss(name = 'CrossEntropy')
    model.to(device)

    #prev value init
    early_stop_flag = 0
    prev_val_loss = float('inf')
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
    class_acc_arr = [0 for i in range(400)]
    class_num_arr = [0 for i in range(400)]
    with torch.no_grad(): 
            model.eval() 
            for i, (imgs, labels) in enumerate(tqdm(dataloader['valid'])):
                inputs = imgs.to(device).float()
                targets = labels.to(device)
                outputs = model(inputs)
                val_loss = criterion(outputs, targets)
                # The label with the highest value will be our prediction 
                _, predicted = torch.max(outputs, 1) 
                _, rank5_predicted = torch.sort(outputs, descending = True)
                running_val_loss += val_loss.item()

                predicted
                total += len(targets)
                running_accuracy += torch.sum(predicted  == targets)
                #acc@5, acc@3
                rank_5_running_accuracy = acc_rank(temp_sum = rank_5_running_accuracy, labels = targets, preds_arr = rank5_predicted, rank = 5)
                rank_3_running_accuracy = acc_rank(temp_sum = rank_3_running_accuracy, labels = targets, preds_arr = rank5_predicted, rank = 3)

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
        # Calculate validation loss value 
        # Calculate accuracy as the number of correct predictions in the validation batch divided by the total number of predictions done. 
            accuracy = (100 * (running_accuracy / total))    
            rank_5_accuracy = (100 * (rank_5_running_accuracy / total))
            rank_3_accuracy = (100 * (rank_3_running_accuracy / total))
            temp_epoch_F1_score = np.mean(f1_arr)
            temp_epoch_acc_score = np.mean(acc_arr)
            temp_epoch_F1_weighted_score = np.mean(f1_weighted_arr)
            np_class_acc_arr = np.divide(class_acc_arr, class_num_arr)
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
inference()