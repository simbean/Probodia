from PIL import Image
import pandas as pd
import numpy as np
import torch
import albumentations as A
import albumentations.pytorch 
import torch.nn.functional as F
from .model import get_model 


def get_image(img_path, s3, bucket_name):
    bucket = s3.Bucket(bucket_name)
    obj = bucket.Object(img_path)
    response = obj.get()
    target = response['Body']
    img = np.array(Image.open(target))
    transform =  A.Compose([
                    A.Resize(300, 300),
                    A.Normalize(),
                    albumentations.pytorch.transforms.ToTensorV2()
                ])
    return transform(image = img)['image'].unsqueeze(0)


def get_predictions(img, paramDataFrame):
    model = get_model()
    model.eval()
    outputs = model.forward(img)
    _, y_hat = torch.sort(outputs, descending = True)
    arr = []
    for i in range(3):
        arr.append(paramDataFrame['음 식 명'][y_hat[0][i].item()])
    return arr