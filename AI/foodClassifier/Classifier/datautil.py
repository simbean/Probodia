from tqdm import tqdm
import os
import random
import matplotlib.pyplot as plt 
from tqdm import tqdm
import albumentations as A
import xml.etree.ElementTree as elemTree
import cv2
import pandas as pd

def unzipTrainimg(zipfilePath, allfilePath):
    f = os.listdir(zipfilePath)
    for folder in f:
        if folder.startswith('[원천]음식분류_TRAIN'):
            print(folder)
            os.system(f'unzip {zipfilePath}/{folder} -d {allfilePath}')



import numpy as np
def setResize(img, annoInfo, standard):
    imgSize = img.shape
    x1, x2, y1, y2 = 0, imgSize[1], 0, imgSize[0]
    xmin, xmax, ymin, ymax = annoInfo
    xmed = (xmin + xmax) // 2 #annotation의 중앙 축
    ymed = (ymin + ymax) // 2 
    #print(f'xmed : {xmed}, ymed : {ymed}, standard : {standard}')
    if 0 <= xmed - standard and xmed + standard <= x2:
        nx1, nx2 = xmed - standard, xmed + standard
    elif xmed - standard < 0:
        nx1 = 0
        nx2 = xmed + standard + (standard - xmed)
    else:
        nx2 = x2
        nx1 = xmed - standard - (xmed + standard - x2)
    if 0 <= ymed - standard and ymed + standard < y2:
        ny1, ny2 = ymed - standard, ymed + standard
    elif ymed - standard < 0:
        ny1 = 0
        ny2 = ymed + standard + (standard - ymed)
    else:
        ny2 = y2
        ny1 = ymed - standard - (ymed + standard - y2)
    
    newImg = img[ny1 : ny2, nx1 : nx2]
    #set anno
    xmin -= nx1
    xmax -= (nx1 + 1)
    ymin -= ny1
    ymax -= (ny1 + 1)
    return newImg, [xmin, ymin, xmax, ymax]


def setPadAndResize(img, annoInfo):
    yImgLen, xImgLen, c = img.shape
    N = max(yImgLen, xImgLen)
    xmin, xmax, ymin, ymax = annoInfo
    xmed = (xmin + xmax) // 2 #annotation의 중앙 축
    ymed = (ymin + ymax) // 2 
    xG  = (N - xImgLen) // 2
    yG = (N - yImgLen) // 2
    padImg = np.pad(img, ((yG, yG), (xG, xG), (0, 0)), mode='constant', constant_values=0)
    xmin += xG
    xmax += xG
    ymin += yG
    ymax += yG
    return setResize(padImg, [xmin, xmax, ymin, ymax], N // 2)



def setResolution(img, info) -> np.ndarray:
    xmin, xmax, ymin, ymax = info
    yImgLen, xImgLen, c = img.shape
    xsize = xmax - xmin #annotation의 길이
    ysize = ymax - ymin

    
    if max(xsize, ysize) > min(xImgLen, yImgLen):
        return setPadAndResize(img, info), 0
    else:
        return setResize(img, info, max(xsize, ysize, min(xImgLen, yImgLen)) // 2), 1
    




def getTrainImg(folderPath, xmlPath, folderName, imgName, small_resize = True):
    name, dotjpg = imgName.split('.')
    xmlName = name + '.xml'
    try:
        x = elemTree.parse(f'{xmlPath}/{folderName}/{xmlName}')
        tree_anno = x.find('./object[1]')
        tree_bbox = tree_anno.find('bndbox')
        xmin = int(tree_bbox.find('xmin').text) 
        xmax = int(tree_bbox.find('xmax').text) 
        ymin = int(tree_bbox.find('ymin').text) 
        ymax = int(tree_bbox.find('ymax').text) 
        img = cv2.imread(f'{folderPath}/{folderName}/{imgName}')
        print(f'{folderPath}/{folderName}/{imgName}')
    except:
        return imgName
    flag = 2
    try:
        if img.shape[0] != img.shape[1]:
            output, flag = setResolution(img, info = [xmin, xmax, ymin, ymax])
            result, bboxInfo = output
        else:
            result = img.copy()
            bboxInfo = [xmin, ymin, xmax, ymax]
        if small_resize:
            h, w, c = result.shape
            transform =  A.Compose([
                        A.Resize(512, 512)], bbox_params = A.BboxParams(format = 'pascal_voc')
                        )
            bboxInfo = bboxInfo + [flag]
            #print(result.shape, bboxInfo)
            transform_img = transform(image = result, bboxes = [bboxInfo])
    except:
        print(2)
        return imgName
    return transform_img['image'], transform_img['bboxes']


def getDF(path, anno, c, dic):
    dic['img_path'].append(path)
    dic['width'].append(512)
    dic['height'].append(512)
    dic['bbox'].append(anno[0][:4])
    dic['format'].append(anno[0][4])
    dic['class'].append(c)
    return dic

def df2csv(df : pd.DataFrame, title : str):
    with open(f'{title}.csv', 'w', encoding = 'utf-8') as csv_file:
        df.to_csv(csv_file)


import os
os.environ["CUDA_DEVICE_ORDER"]="PCI_BUS_ID" 
os.environ["CUDA_VISIBLE_DEVICES"]="0"
#[283, 290]
path1 = 'F:/trainimgimg/Training'
path2 = 'D:/xml/xml'
folderName = '11013012'
nottransImg = {'failed' : []}
dic = {'img_path' : [],
        'width' : [], 'height' : [],
        'bbox' : [], 'class' : [], 'format' : []}

for imgName in tqdm(os.listdir(f'F:/trainimgimg/Training/{folderName}')):
    output = getTrainImg(path1, path2, folderName, imgName)
    
    if isinstance(output, str):
        nottransImg['failed'].append(output)
        continue
    timg, bbox = output
    new_path = f'F:/trainImage2/trainImage3/{imgName}'
    cv2.imwrite(new_path, timg)
    dic = getDF(imgName, bbox, folderName, dic)


df = pd.DataFrame(dic)
df2csv(df, '11013012')
print(len(nottransImg))