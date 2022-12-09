import pickle
from xgboost import XGBRegressor

def get_model():
    model = XGBRegressor(booster = 'gbtree')
    model.load_model('/run/secrets/model')
    return model

