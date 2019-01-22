# __author__: Mai feng
# __file_name__: json_demo.py
# __time__: 2019:01:22:15:57

import json
class Json(object):
    def __init__(self, file_name):
        '''初始化
        :param file_name: 文件名字
        '''
        self.file_name = file_name

    def wrtie(self, datas):
        '''json文件里写数据
        :param datas: 可以是列表，也可以是字典，不可以是类对象
        :return: 写入成功返回true，否则false
        '''
        res = False
        try:
            with open(self.file_name + '.json', 'w', encoding='utf-8') as f:
                json.dump(datas, f, ensure_ascii=False)
            return res
        except Exception as e:
            print('write->error:', e)
            return res

    def read(self):
        '''从json文件中读取数据
        :return: 返回列表或者字典，看文件内容。。。
                 异常返回空None
        '''
        res = None
        try:
            with open(self.file_name + '.json', 'r', encoding='utf-8') as f:
                res = json.load(f)
            return res
        except Exception as e:
            print('read->error:', e)
            return None
        

if __name__ == "__main__":
    demo_list = [
        {
            'name':'test1',
            'age':'1',
        },
        {
            'name':'test2',
            'age':'2'
        }
    ]
    _json = Json('demo')
    _json.wrtie(demo_list)
    datas = _json.read()
    print(datas)