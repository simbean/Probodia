import torch
from torch import nn
from torchvision import models

class efficientNetV2M(nn.Module):
    def __init__(self, in_channels, device = 'cpu'):
        super(efficientNetV2M, self).__init__()
        self.device = device
        self.network = models.efficientnet_v2_s()
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

def get_model():
    device = torch.device('cpu')
    model = efficientNetV2M(in_channels=400)
    model.load_state_dict(torch.load('weight/model8.pt', map_location=device))
    return model