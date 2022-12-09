


from torch import nn


def selectLoss(name : str):
    if name == "CrossEntropy":
        criterion = nn.CrossEntropyLoss()
    return criterion