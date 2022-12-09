import torch
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms, utils
import pandas as pd
import cv2
import albumentations as A
import albumentations.pytorch 

class FoodDataset(Dataset):
    def __init__(self, csv_file, root_dir, transform = None):
        """
        Args:
            csv_file (string): csv 파일의 경로
            root_dir (string): 모든 이미지가 존재하는 디렉토리 경로
            transform (callable, optional): 샘플에 적용될 Optional transform
        """
        self.df = pd.read_csv(csv_file)
        self.root_dir = root_dir
        self.transform = transform

    def __len__(self):
        return len(self.df)

    def __getitem__(self, idx):
        folder = self.df.iloc[idx]['folder']
        img_name = self.df.iloc[idx]['img_path']
        target = cv2.imread(f'{folder}{img_name}')
        target = cv2.cvtColor(target, cv2.COLOR_BGR2RGB)
        label = self.df.iloc[idx]['label']

        if self.transform:
            augmented = self.transform(image=target) 
            image = augmented['image']

        return image, label




def setDataloader(csv_file_list, root_dir, batch_size, img_size, transform):
    train_path, valid_path = csv_file_list

    train_dataset = FoodDataset(
        csv_file = train_path,
        root_dir = root_dir,
        transform = transform
    )

    valid_dataset = FoodDataset(
        csv_file = valid_path,
        root_dir = root_dir,
        transform =  A.Compose([
                            A.Resize(img_size, img_size),
                            A.Normalize(),
                            albumentations.pytorch.transforms.ToTensorV2()
                        ])
    )

    dataloader = {}
    dataloader['train'] = DataLoader(train_dataset,
                                batch_size=batch_size,
                                num_workers = 2,
                                shuffle=True)
    
    dataloader['valid'] = DataLoader(valid_dataset,
                                    batch_size=batch_size,
                                    num_workers = 2,
                                    shuffle=True)
    
    return dataloader