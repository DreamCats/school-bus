'''
@author:Mai feng

'''
import os
from numpy import *

class MyFileTools(object):
    def __init__(self):
        pass

    '''
    获取文件列表

    '''
    def get_folder_list(self,folder):
        file_list = os.listdir(folder)
        return file_list
    '''
    获取文件标签属性

    '''
    def get_file_label(self,file_name):
        file_str = file_name.split('.')[0]
        label_current = file_str.split('_')[0]
        return label_current

    '''
    制作文件数组

    '''
    def get_file_array(self,file_name,size):
        arr = []
        fh = open(file_name)
        for y in range(0,size[1]):
            this_line = fh.readline()
            for x in range(0,size[0]):
                arr.append(int(this_line[x]))
        return arr

    '''
    制作训练文件数组

    '''
    def get_trian_array(self,traindata_folder,size):
        train_labels= []
        train_files = os.listdir(traindata_folder)
        train_num = len(train_files)
        # 用一个数组存储所有训练数据，行：文件总数，列：1024
        train_array = zeros((train_num, int(size[0]) * int(size[1])))
        for i in range(0, train_num):
            thisname = train_files[i]
            thiscate = self.get_file_label(thisname)
            train_labels.append(thiscate)
            train_array[i, :] = self.get_file_array(traindata_folder + thisname, size)
        return train_array, train_labels