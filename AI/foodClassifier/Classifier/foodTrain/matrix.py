import torch


def acc_rank(temp_sum, labels, preds_arr, rank):
    for label, preds in zip(labels, preds_arr):
        if label in preds[:rank]:
            temp_sum += 1
    return temp_sum


'''def rank_precision(labels, pred_arr, rank):
    true_pos = 0
    false_pos = 0
    for label, preds in zip(labels, pred_arr):
        if label in preds[:rank]'''