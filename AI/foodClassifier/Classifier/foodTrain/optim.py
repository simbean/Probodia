import torch.optim as optim

def selectOptimizer(name : str, model, param_lr):
    optimizer = ''
    if name == "adamW":
        optimizer = optim.AdamW(model.parameters(),
                               lr = param_lr)
    elif name == 'SGD':
        optimizer = optim.SGD(model.parameters(),
                              lr= param_lr, 
                              momentum=0.9,
                              weight_decay = 1e-4)
    
    return optimizer    