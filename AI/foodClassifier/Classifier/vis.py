from PIL import Image
from tqdm import tqdm
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt
import os


def dict2hist(dic1 : dict):
    x = list(dic1.keys())
    df = pd.DataFrame({'x' : x})
    graph = sns.histplot(x = 'x', data = df)
    graph.fig.set_size_inches(20, 20)
    plt.savefig('image.png')


def dict2bar(dic1 : dict, xsize : int, ysize : int, title : str, minmaxVis = True, log_scale = False):
    x = list(dic1.keys())
    y = list(dic1.values())

    ax = plt.subplots(figsize=(xsize, ysize))
    df = pd.DataFrame({'x' : x, 'y' : y})
    ax = sns.barplot(data = df, 
                    x = 'x', 
                    y = 'y',
                    color = 'gray')
    ax.axes.xaxis.set_visible(False)
    if log_scale is True:
        ax.set(xscale = 'log')
    if minmaxVis is True:
        box = {'boxstyle': 'round',
        'ec': (0., 0., 0.),
        'fc': (1., 1., 1.)}

        n = list(dic1.items())
        min_name, min_num = min(n, key= lambda x : x[1])
        max_name, max_num = max(n, key = lambda x : x[1])
        patch = ax.patches
        minp = patch[min_name - 1]
        maxp = patch[max_name - 1]
        min_height = minp.get_height()
        max_height = maxp.get_height()
        ax.text(minp.get_x() + minp.get_width() / 2., 
                min_height - 5, 
                f'min num photo (class = {min_name}, num = {int(min_num)})', 
                ha = 'center',
                size = 9,
                bbox = box)
        ax.text(maxp.get_x() + maxp.get_width() / 2., 
                max_height + 5, 
                f'max num photo (class = {max_name}, num = {int(max_num)})', 
                ha = 'center', 
                size = 9,
                bbox = box)
    plt.savefig(f'{title}.png')



def file2scatter(path):
    for temp_file in tqdm(os.listdir(path)):
        print(temp_file)
