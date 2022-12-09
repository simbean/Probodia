import argparse
from foodTrain.train import *

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description= 'train')
    

    parser.add_argument('--epoch', type=int, dest = "epoch", default=20)
    parser.add_argument('--batch_size', type=int, dest = "batch_size", default=4)
    parser.add_argument('--learning_late', type=float, dest = "lr", default=1e-4)
    parser.add_argument('--class_num', type = int, dest= "class_num", default = 400)
    
    parser.add_argument('--model', type = str, dest= "model", default = "efficientNetV2")
    parser.add_argument('--optimizer', type = str, dest= "optimizer", default = "adamW")
    parser.add_argument('--loss', type = str, dest= "loss", default = "CrossEntropy")
    parser.add_argument('--imagesize', type = str, dest= "img_size", default = 300)
    parser.add_argument('--freeze', type = str, dest= "freeze", default = 20)
    parser.add_argument('--early_stop', type = str, dest= "early_stop", default = 3)
    parser.add_argument('--model_name', type = str, dest= "name", default = "efficientV2aug")
    parser.add_argument('--scheduler', type = str, dest= "scheduler", default = "CosineAnnealingWarmRestarts")
    parser.add_argument('--load_weight', type = str, dest= "load_weight", default = "None")
    args = parser.parse_args()
    train(args)