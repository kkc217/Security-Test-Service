import sys
import os
import tlsh
import csv
import pickle
import pandas as pd
import joblib
import numpy as np
import xgboost as xgb
from sklearn import metrics
from sklearn.model_selection import train_test_split
# from sklearn.metrics import balanced_accuracy_score, roc_auc_score, make_scorer
# from sklearn.model_selection import GridSearchCV
# from sklearn.metrics import confusion_matrix
# from sklearn.metrics import plot_confusion_matrix
from sklearn.preprocessing import LabelEncoder
# from sklearn.ensemble import GradientBoostingClassifier


def split_data_xy(origin_path) :
    data_x = []
    data_y = []
    with open(origin_path, 'r') as full_data :
        data = full_data.readlines()
        for file in data :
            if file.split(',')[0] == 'Label' : continue
            data_x.append(str(file.split(',')[2]).replace('\n', ''))
            data_y.append(file.split(',')[0])
    return data_x, data_y


def divide_per_label(x_train, y_train) :
    labels = [[],[],[],[],[],[],[],[],[]]
    label_size = []
    
    for i in range(len(x_train)) :
        label = y_train[i]
        if label == 1 :
            labels[0].append(x_train[i])
        elif label == 2 :
            labels[1].append(x_train[i])
        elif label == 3 :
            labels[2].append(x_train[i])
        elif label == 4 :
            labels[3].append(x_train[i])
        elif label == 5 :
            labels[4].append(x_train[i])
        elif label == 6 :
            labels[5].append(x_train[i])
        elif label == 7 :
            labels[6].append(x_train[i])
        elif label == 8 :
            labels[7].append(x_train[i])
        elif label == 9 :
            labels[8].append(x_train[i])
    
    for i in range(9) :
        label_size.append(len(labels[i]))
    
    return labels, label_size


def find_best_digest(labels) :
    best_digest = []
    for i in range(9) :
        digest = ''
        min_score = 1000000000000000000000000000000000000000000000
        for target_file in labels[i] :
            score = 0
            for compare_file in labels[i] :
                score += tlsh.diff(target_file, compare_file)
            if (score < min_score) :
                min_score = score
                digest = target_file
        
        best_digest.append(digest)
    return best_digest

def predict_by_tlsh(target, best_digest, threshold) :  
    min_score = 10000000
    pred_label = 0
    for i in range(len(best_digest)) :
        score = tlsh.diff(target, best_digest[i])
        if score < min_score : 
            min_score = score
            pred_label = i
            
    if min_score <= threshold :
        return pred_label
    else : 
        return -1
    

def convert_char(c) :
    tmp = 0
    if c.isdigit() :
        tmp = int(c)
    else : 
        if (c == 'A') : tmp = 10
        if (c == 'B') : tmp = 11
        if (c == 'C') : tmp = 12
        if (c == 'D') : tmp = 13
        if (c == 'E') : tmp = 14
        if (c == 'F') : tmp = 15
    return tmp  


def convert_digest_to_int(x_left) :
    x_left_int = []
    x_left_int.append(['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50', '51', '52', '53', '54', '55', '56', '57', '58', '59', '60', '61', '62', '63', '64', '65', '66', '67', '68', '69'])
    
    for i in range(len(x_left)) :
        tmp = x_left[i][2:]
        tmp_list = []
        for c in tmp :
            tmp_list.append(convert_char(c))
        x_left_int.append(tmp_list)
    return x_left_int

def convert_target_to_int(target) :
    x_left_int = []
    x_left_int.append(['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50', '51', '52', '53', '54', '55', '56', '57', '58', '59', '60', '61', '62', '63', '64', '65', '66', '67', '68', '69'])
    
    tmp = target[2:]
    tmp_list = []
    for c in tmp :
        tmp_list.append(convert_char(c))
    x_left_int.append(tmp_list)
    return x_left_int

def make_xgb_df(target) :
    target_int = convert_target_to_int(target)
    target_arr = np.array(target_int)
    target_df = pd.DataFrame(target_arr[1:], columns=target_arr[0])
    target_df = target_df.applymap(int)
    # print(target_df)
    return target_df

def match_label(label_idx) :
    name = ''
    if label_idx == 0 :
        name = 'Ramnit'
    elif label_idx == 1 :
        name = 'Lollipop'
    elif label_idx == 2 :
        name = 'Kelihos_ver3'
    elif label_idx == 3 :
        name = 'Vundo'
    elif label_idx == 4 :
        name = 'Simda'
    elif label_idx == 5 :
        name = 'Tracur'
    elif label_idx == 6 :
        name = 'Kelihos_ver1'
    elif label_idx == 7 :
        name = 'Obfuscator.ACY'
    elif label_idx == 8 :
        name = 'Gatak'
    else :
        name = 'Cannot find :('
    return name

def make_pred(target, data_x, data_y) :
    x_train = data_x
    y_train = list(map(int, data_y))
    
    le = LabelEncoder()
    y_train_xgb = le.fit_transform(y_train)
    
    load_model = joblib.load('./model/tlsh_xgb_model1.pkl')
    
    # make_xgb(x_train, y_train_xgb)
    
    labels, label_size = divide_per_label(x_train, y_train)
    # best_digest = find_best_digest(labels)
    best_digest = ['T1F6940781C1DF82C1E62B594C5834BB92002BB473BBC94972132F5676DFAED47258FA8D', 
                   'T18FB5839CF24368D1D70F92AC18B079AE097375E761CA04341FAA44F2AE48CDEB9DB51D', 
                   'T12326AFCA916B44D2DD053FC59C343AC74B2876335AE40014366BBE798F6B4FA809FEA5', 
                   'T1B6848DDD91AF44D6DC076E8098343A83472172724AFC0098276FB9784B676F7B05FEA6', 
                   'T125A59ED8806F44D1EC062EC6B8787AC31B73B6734DD41824377E7A744F2B9A9848BE59', 
                   'T103359EC9C15B84C1FC052BC568743AC347317BB357E84025266BB9788BB79FA844FDAA', 
                   'T198D5315142AFC2E1E9070D40BC74F6B1187170ABAEC55D33666E7635CEBE912226FF09', 
                   'T1154419F1985B8481C43615817435BBA6C52B3573BECC883E223BB478DE7FC05671BA6A', 
                   'T1D9D4D99064DD8A91F1119E80B934FBF2027374339EDD1CB62129BA04DEBF592325EA5F']
    
    
    threshold = 80
    pred_label = predict_by_tlsh(target, best_digest, threshold)
    
    if (pred_label == -1) :
        target_df = make_xgb_df(target)
        xgb_pred = load_model.predict(target_df)
        return match_label(xgb_pred[0])
    else :
        return match_label(pred_label)

def make_target(target_path) :
    target = ''
    with open(target_path, 'rb') as binary :
        target = tlsh.hash(binary.read())
    return target

def service(target_path) :
    origin_path = "./model/Label_tlsh.csv"
    target = make_target(target_path)
    data_x, data_y = split_data_xy(origin_path)
    print(make_pred(target, data_x, data_y))
    
def main(target_path) :
    service(target_path)

if __name__ == "__main__":
    main(sys.argv[1])
    
    