import json
import pandas as pd
import copy
from typing import Any, List


def calculateGL(data):
    data = float(data)
    if data < 0:
        return 0
    elif data > 100:
        return 100

    if data < 10:
        return round(data, 3)
    elif 10 <= data <= 20:
        return data
    else:
        return round(data, 3)

def selectLable(data):
    if data < 10:
        return 0
    elif 10 <= data <= 20:
        return 1
    else:
        return 2

def mapping(data):
    if data == 0:
        return 'low'
    elif data == 1:
        return 'mid'
    else:
        return 'high'

def make_query(param_df : pd.DataFrame, weight : int):
    arr = []
    for i in range(len(param_df)):
        G = weight / param_df.iloc[i]['quantity_by_one']
        query = [G * max(param_df.iloc[i]['carbohydrate'], param_df.iloc[i]['sugars']), G * param_df.iloc[i]['fat'],
                (G * param_df.iloc[i]['fiber']) ** 2, (G * param_df.iloc[i]['protein']) ** 2]
        arr.append(copy.deepcopy(query))
    return arr

def predict(query : List[List[float]], model : Any):
    return model.predict(query)


def select_food(df : pd.DataFrame, food_label : int, type : str, code : int, amount : int, model : Any):
    if food_label == 0:
        return -1, -1, -1
    try:
        sub_df = df.loc[df[type] == code]
        preds = predict(make_query(sub_df, amount), model)
        label = [selectLable(value) for value in preds]
        sub_df['label'] = label
        sub_df['GL'] = preds
        sub_df = sub_df.loc[sub_df['label'] < food_label]
        select_food = sub_df.sample(n = 1)
    except:
        return -1, -1, -1
    return select_food['id'].values[0], float(select_food['GL'].values[0]), int(select_food['label'].values[0])