from fastapi import FastAPI, Depends, HTTPException, status
from pydantic import BaseModel
import uvicorn
import json
from jose import JWTError, jwt
from fastapi.security import OAuth2PasswordBearer
from  function.connect import get_DataFrame, get_engine
from function.choice import *
from function.logger import create_logger
from function.model import get_model
from typing import Dict, List, Any
import numpy as np
with open("/run/secrets/coef") as f:
    #with open("/home/ubuntu/GL.json") as f:
    inform = json.load(f)

ACCESS_KEY = inform['ACCESS_KEY']
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 30
CAR, FAT, FIB, PRO = 0, 1, 2, 3

logstash_server_url = inform['LOGSTASH_SERVER']
engine = get_engine()
df1  = get_DataFrame(inform['GL_DB'], engine)[['id', 'sugars']]
df1 = df1[['id', 'sugars']]
df2  = get_DataFrame(inform['GL_DB2'], engine)
df = pd.merge(left = df1, right = df2, how = 'left', on = 'id')
df.replace(np.nan, 0, inplace = True)


app = FastAPI()
model = get_model()
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")
logger_one = create_logger('one-food-predict', logstash_server_url)
logger_all = create_logger('all-food-predict', logstash_server_url)


class Token(BaseModel):
    access_token: str
    token_type: str

class oneFood(BaseModel):
    foodId : str
    bigCategory: str
    smallCategory: str
    calories: float
    carbohydrate: float
    cholesterol: float
    fat: float
    name: str
    protein: float
    quantityByOne: float
    quantityByOneUnit: str
    salt: float
    saturatedFat: float
    sugars: float
    transFat: float


class allFood(BaseModel):
    foodList : List[Dict[str, Any]]

async def get_current_user(token: str = Depends(oauth2_scheme)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(token, ACCESS_KEY, algorithms=[ALGORITHM])
        username: str = payload.get("sub")
        if username is None:
            raise credentials_exception
    except JWTError:
        raise credentials_exception
    return username

@app.post("/oneGL")
async def predict_GL(item : oneFood, 
                     current_user: str = Depends(get_current_user)):
    logger_one.info(current_user)
    print(item.foodId)
    idx = df.loc[df['id'] == item.foodId].index[0]
    G = item.quantityByOne / df.iloc[idx]['quantity_by_one'] 
    q = [[G * max(df.iloc[idx]['carbohydrate'],df.iloc[idx]['sugars']) , G * df.iloc[idx]['fat'], (G * df.iloc[idx]['fiber']) ** 2, (G * df.iloc[idx]['protein']) ** 2]]
    pred = predict(q, model)
    target_foodGL, target_healthGL = calculateGL(pred[0]), selectLable(pred[0])
    big_category_food, big_category_GL, big_category_label = select_food(df, target_healthGL, 'big_category', df.iloc[idx]['big_category'], item.quantityByOne, model)
    small_category_food, small_category_GL, small_category_label = select_food(df, target_healthGL, 'small_category', df.iloc[idx]['small_category'], item.quantityByOne, model)
    return { 
             "main" : {"foodId" : item.foodId, "foodGL" : target_foodGL, "healthGL" : mapping(target_healthGL)},
             "foodBig" : {"foodId" : '0' if big_category_food == -1 else big_category_food,
                          "foodGL" : target_foodGL if big_category_GL == -1 else calculateGL(big_category_GL),
                          "healthGL" : mapping(target_healthGL) if big_category_label == -1 else mapping(big_category_label)
                          },
             "foodSmall" : {"foodId" : '0' if small_category_food == -1 else small_category_food,
                            "foodGL" : target_foodGL if small_category_GL == -1 else small_category_GL,
                            "healthGL" : mapping(target_healthGL) if small_category_label == -1 else mapping(small_category_label)
                            },
            } 

    
@app.post("/allGL")
async def predict_GL(item : allFood, 
                     current_user: str = Depends(get_current_user)):

    logger_all.info(current_user)
    ingredient = [0, 0, 0, 0]
    for query in list(item.foodList):
        foodId, foodAmount = query.values()
        print(foodId)
        idx = df.loc[df['id'] == foodId].index[0]
        G = foodAmount / df.iloc[idx]['quantity_by_one'] 
        ingredient[CAR] += G * max(df.iloc[idx]['carbohydrate'], df.iloc[idx]['sugars'])
        ingredient[FAT] += G * df.iloc[idx]['fat']
        ingredient[FIB] += (G * df.iloc[idx]['fiber']) ** 2
        ingredient[PRO] += (G * df.iloc[idx]['protein']) ** 2
    
    pred = predict([ingredient], model)
    return {
            "foodGL": calculateGL(pred[0]),
            "healthGL" : mapping(selectLable(pred[0]))
            }



if __name__ == '__main__':
    uvicorn.run('main:app', host='0.0.0.0', port = 8000,
               ssl_certfile="/run/secrets/cert.pem", ssl_keyfile="/run/secrets/key.pem")
    #uvicorn.run('main:app', host='0.0.0.0', port = 8000,
    #       ssl_certfile="/home/ubuntu/cert.pem", ssl_keyfile="/home/ubuntu/key.pem")
