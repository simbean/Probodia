from flask import Flask, jsonify, request  # 서버 구현을 위한 Flask 객체 import

from flask_restx import Api, Resource  # Api 구현을 위한 Api 객체 import
import torch
import cv2
from model import efficientNetV2M
import pandas as pd
import numpy as np
import albumentations as A
import albumentations.pytorch 
import torch.nn.functional as F


app = Flask(__name__)  # Flask 객체 선언, 파라미터로 어플리케이션 패키지의 이름을 넣어줌.
api = Api(app)  # Flask 객체에 Api 객체 등록

def get_model():
    model = efficientNetV2M(in_channels=401)
    model.load_state_dict(torch.load('efficientV2aug.pt'))
    return model


def get_image(img_path):
    target = cv2.imread(img_path)
    target = cv2.cvtColor(target, cv2.COLOR_BGR2RGB)
    transform =  A.Compose([
                    A.Resize(300, 300),
                    A.Normalize(),
                    albumentations.pytorch.transforms.ToTensorV2()
                ])
    return transform(image = target)['image'].unsqueeze(0)


def get_predictions(img):
    df = pd.read_csv('../aidb.csv', encoding = 'cp949')
    model = get_model()
    model.eval()
    outputs = model.forward(img)
    _, y_hat = torch.sort(outputs, descending = True)
    print(outputs.sort())
    arr = []
    for i in range(3):
        arr.append(df['음 식 명'][y_hat[0][i].item() - 1])
    return arr
    
@app.route('/predict', methods=["GET", "POST"])
def predict():
    if request.method == "GET": 
        message = {
            "name" : "GET"
        }
        return message

    if request.method == 'POST':
        file = request.get_json()
        img_path = file['img_path']
        img = get_image(img_path = img_path)
        class_id = get_predictions(img)
        print(class_id)
        return jsonify({'class_id': class_id})

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=80)