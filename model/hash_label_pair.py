import os
import tlsh
import csv     
        
if __name__=='__main__' :
    binary_path = "E:/Malware/BIG2015/train/s_to_0_bytes"
    label_path = "E:/Malware/BIG2015/trainLabels.csv"
    save_path = "E:/Malware/BIG2015/train/del0_label_tlsh.csv"
    
    # with open(label_path, 'r') as csvfile :
    #     labels = csv.reader(csvfile)
    #     for line in labels :
    #         print(line[0])

    # make csv file to save label and hash pair
    with open(label_path, 'r') as csvfile :
        labels = csv.reader(csvfile)
        with open(save_path, 'w', newline='') as label_tlsh_pair :
            pair = csv.writer(label_tlsh_pair)
            pair.writerow(['Label', 'File', 'TLSH'])
            for label in labels :
                tmp = []
                if label[0] == 'Id' : continue
                binary_file = binary_path + '/' + label[0] + '.bytes'
                tmp.append(label[1])
                tmp.append(label[0])
                with open(binary_file, 'rb') as binary :
                    digest = tlsh.hash(binary.read())
                    tmp.append(digest)
                pair.writerow(tmp)

