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
        '''向json文件里写数据
        '''
demo = {
    'name':'maifeng',
    'age':'1'
}
demo1 = {
    'name': 'test',
    'age':'2'
}
demo_list = [demo, demo1]
# with open('demo_json.json', 'w', encoding='utf-8') as f:
#     json.dump(demo_list, f, ensure_ascii=False)
with open('demo_json.json', 'r') as f:
    datas = json.load(f)
    print(datas)
    print(type(datas))