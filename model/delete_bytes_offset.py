import os
import sys

if __name__=='__main__' :
    
    train_path = "E:/Malware/BIG2015/train/train"
    save_path = "E:/Malware/BIG2015/train/delete_offset_bytes"
    
    train_list = os.listdir(train_path)
    bytes_list = []

    for i in range(len(train_list)) :
        if (i%2 == 1) :
            bytes_list.append(train_list[i])

    for bytes in bytes_list :
        bytes_file_name = train_path+"/"+bytes
        save_file_name = save_path+"/"+bytes
        with open(bytes_file_name, 'r') as origin_file :
            origin_lines = origin_file.readlines()
            # print(origin_lines[0])
            with open(save_file_name, 'w') as new_file : 
                for i in range(len(origin_lines)):
                    new_line = origin_lines[i][8:]
                    real_line = new_line.replace(' ','')
                    real_line = real_line.replace('\n','')
                    new_file.write(real_line)

    new_file_list = os.listdir(save_path)
    
    
    # To check reduce rate
    rate_sum = 0
    for i in range(len(new_file_list)):
        origin_size = os.path.getsize(train_path+"/"+bytes_list[i])
        new_size = os.path.getsize(save_path+"/"+new_file_list[i])
        decrease_rate = (new_size/origin_size)*100
        # print(decrease_rate)
        rate_sum += decrease_rate
        
    rate_avg = rate_sum/10868