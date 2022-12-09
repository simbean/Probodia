from efficientnet_pytorch import EfficientNet
import torch
from torch import nn
from torchvision import models
class efficientNetB1(nn.Module):
    def __init__(self, in_channels, device = 'cpu'):
        super(efficientNetB1, self).__init__() #부모 클래스 초기화
        self.device = device
        self.network = EfficientNet.from_pretrained('efficientnet-b1', num_classes= in_channels)
    def forward(self, x):
        return self.network(x).to(self.device)

    def freeze(self):
        for param in self.network.parameters():
            param.requires_grad = False

        for param in self.network.fc.parameters():
            param.requires_grad = True
    
    def unfreeze(self):
        for param in self.network.parameters():
            param.requires_grad = True

class efficientNetB3(nn.Module):
    def __init__(self, in_channels, device):
        super(efficientNetB3, self).__init__() #부모 클래스 초기화
        self.device = device
        self.network = EfficientNet.from_pretrained('efficientnet-b3', num_classes= in_channels)
    def forward(self, x):
        return self.network(x).to(self.device)

    def freeze(self):
        for param in self.network.parameters():
            param.requires_grad = False

        for param in self.network.fc.parameters():
            param.requires_grad = True
    
    def unfreeze(self):
        for param in self.network.parameters():
            param.requires_grad = True




class efficientNetB4(nn.Module):
    def __init__(self, in_channels, device):
        super(efficientNetB4, self).__init__() #부모 클래스 초기화
        self.device = device
        self.network = EfficientNet.from_pretrained('efficientnet-b4', num_classes= in_channels)
    def forward(self, x):
        return self.network(x).to(self.device)

    def freeze(self):
        for param in self.network.parameters():
            param.requires_grad = False

        for param in self.network.fc.parameters():
            param.requires_grad = True
    
    def unfreeze(self):
        for param in self.network.parameters():
            param.requires_grad = True



class efficientNetV2M(nn.Module):
    def __init__(self, in_channels, device = 'cpu'):
        super(efficientNetV2M, self).__init__() #부모 클래스 초기화
        self.device = device
        self.network = models.efficientnet_v2_s(weights = 'IMAGENET1K_V1')
        self.network.classifier[1] = nn.Linear(in_features=1280, out_features=in_channels, bias=True)
    def forward(self, x):
        return self.network(x).to(self.device)

    def freeze(self):
        for param in self.network.parameters():
            param.requires_grad = False

        for param in self.network.classifier.parameters():
            param.requires_grad = True
    
    def unfreeze(self):
        for param in self.network.parameters():
            param.requires_grad = True


def selectModel(name : str, class_num : int, device):
    if name == "efficientNetB1":
        return efficientNetB1(class_num, device)
    elif name == "efficientNetB3":
        return efficientNetB1(class_num, device)
    elif name == "efficientNetB4":
        return efficientNetB4(class_num, device)
    elif name == "efficientNetV2":
        return efficientNetV2M(class_num, device)