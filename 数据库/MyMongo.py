# -*- coding: utf-8 -*-
# @Author: maifeng
# @Date:   2017-12-01 20:51:39
# @Last Modified by:   Marte
# @Last Modified time: 2017-12-01 22:58:06
import pymongo
class MyMongo(object):
    """
    docstring for ClassName
    mongo_url:连接mongodb的url地址
    mongo_db:数据库名称
    """
    def __init__(self, mongo_url,mongo_db):
        super(MyMongo, self).__init__()
        self.mongo_url = mongo_url
        self.mongo_db = mongo_db

    '''
    连接mongodb本地数据库的方法

    '''
    def set_open_client(self):
        self.client = pymongo.MongoClient(self.mongo_url)
        self.db = self.client[self.mongo_db]

    '''
    关闭数据库的方法

    '''
    def set_close_client(self):
        self.client.close()

    '''
    添加数据
    data_dict
    '''
    def set_add_data(self,form,data_dict,repeat=False):
        if repeat:
            data   = self.set_find_one(form,data_dict)
            if data:
                print('用重复数据')
                return None
            else:
                result = self.db[form].insert_one(data_dict)
                return result
        else:
            result = self.db[form].insert_one(data_dict)
            return result
    '''
    添加数据
    data_dict_list
    '''
    def set_add_datas(self,form,data_dict_list):
        result = self.db[form].insert_many(data_dict_list)
        return result
    '''
    查询数据
    data_dict
    '''
    def set_find_one(self,form,data_dict):
        result = self.db[form].find_one(data_dict)
        return result
    '''
    查询数据
    data_dict_list
    '''
    def set_find(self,form,data_dict_list=None):
        results = self.db[form].find(data_dict_list)
        return results
    '''
    更新数据

    '''
    def set_updata(self,form,data_dict,updata_dict):
        data = self.db[form].find_one(data_dict)
        keys = tuple(updata_dict.keys())
        print(keys)
        if keys:
            data[keys[0]] = updata_dict[keys[0]]
            result = self.db[form].update_one(data_dict,{'$set':data})
            return result
    '''
    删除数据

    '''
    def set_delete_data(self,form,data_dict):
        result = self.db[form].delete_one(data_dict)
        return result

    '''
    删除全部数据

    '''
    def set_delete_all(self,form):
        result = self.db[form].remove()
        return result

