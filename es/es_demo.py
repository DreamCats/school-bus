# __author__: Mai feng
# __file_name__: es_demo.py
# __time__: 2019:03:09:10:58

from elasticsearch_dsl import Document, analyzer, Keyword, Text
from elasticsearch_dsl.connections import connections
from elasticsearch_dsl import Search, Q
from elasticsearch import Elasticsearch



class DemoInitAPI:
    '''初始化es'''
    def __init__(self):
        # 连接接口服务器
        connections.create_connection(hosts=["localhost"])
        # 初始化分词 analyzer('ik_smart')

        DemoTeacherIndex.init()
        DemoStudentIndex.init()




class DemoTeacherIndex(Document):
    name = Text(analyzer=analyzer('ik_smart'))
    user_id = Keyword()
    age  = Keyword()
    gender = Keyword()
    content = Text(analyzer=analyzer('ik_smart'))
    class Index:
        # 表名
        name = 'teacher'

class DemoStudentIndex(Document):
    name = Text(analyzer=analyzer('ik_smart'))
    user_id = Keyword()
    age  = Keyword()
    gender = Keyword()
    content = Text(analyzer=analyzer('ik_smart'))
    class Index:
        # 表名
        name = 'student'





class DemoESAPI(object):
    def __init__(self, user_index, user_class_index):
        '''初始化
        :param user_index: index
        :param user_class_index: 实例化index
        '''
        # 连接服务器
        connections.create_connection(hosts=["localhost"])
        # 初始化客户端
        client = Elasticsearch()
        # 检索操作api
        self.search = Search(using=client, index=user_index)
        # 初始化id
        self.rand_num  = 5
        # 初始化id字符串
        self.rand_words = 'zxcvbnmlkjhgfdsaqwertyuiopPOIUYTREWQASDFGHJKLMNBVCXZ1234567890'

        self.index = user_class_index
    
    def save(self, item):
        '''添加items
        :param item: 字典格式的数据
        '''
        self.index.meta.id = item['user_id']
        self.index.user_id = item['user_id']
        self.index.name = item['name']
        self.index.age = item['age']
        self.index.gender = item['gender']
        self.index.content = item['content']
        self.index.save()

    def delete_all(self):
        '''全部删除
        没有参数
        '''
        q = Q('match_all') # 构造q语句
        self.search = self.search.query('bool', must=[q])
        self.search.delete()

    def delete(self, _id):
        '''删除指定的data
        :param _id: 数据的id(_id)
        '''
        item = self.index.get(id=_id)
        item.delete()

    def get_data_by_id(self, _id):
        '''通过id找数据
        :param _id: 数据id(_id)
        :return: 返回index类
        '''
        item = self.index.get(id=_id, ignore=404)
        return item
        
    def get_data_by_field(self, key, value):
        '''通过某个字段找全部数据
        :param key: 字段 str
        :param value: 值 str
        :return: 返回数据列表
        '''
        data_list = []
        q = Q('multi_match', query=value, fields=[key]) # 构造q语句
        self.search = self.search.query('bool', must=[q])
        response = self.search.execute() 
        # 获取全部数目
        total_num = response['hits']['total']
        self.search = self.search[0:total_num]
        # 再次执行获取数据
        response = self.search.execute()
        # 构造列表
        for hit in response['hits']['hits']:
            data_dict = {}
            data_dict['id'] = hit['_id']
            data_dict['name'] = hit['_source']['name']
            data_dict['age'] = hit['_source']['age']
            data_dict['gender'] = hit['_source']['gender']
            data_dict['content'] = hit['_source']['content']
            data_dict['user_id'] = hit['_source']['user_id']
            data_list.append(data_dict)
        return data_list

    def get_data_all(self):
        '''获取index的全部数据
        :return: 返回数据列表
        '''
        data_list = []
        q = Q('match_all') # 构造q语句
        self.search = self.search.query('bool', must=[q])
        response = self.search.execute() 
        # 获取全部数目
        total_num = response['hits']['total']
        self.search = self.search[0:total_num]
        # 再次执行获取数据
        response = self.search.execute()
        # 构造列表
        for hit in response['hits']['hits']:
            data_dict = {}
            data_dict['id'] = hit['_id']
            data_dict['name'] = hit['_source']['name']
            data_dict['age'] = hit['_source']['age']
            data_dict['gender'] = hit['_source']['gender']
            data_dict['content'] = hit['_source']['content']
            data_dict['user_id'] = hit['_source']['user_id']
            data_list.append(data_dict)
        return data_list

    def get_data_by_words(self, words, s_n=0, e_n=10):
        '''通过关键字找数据(对应fields) 多字段
        :param words: 用户关键词
        :param s_n: 开始默认数目10个
        :param e_n: 结束默认数目10个
        :return: 返回数据列表
        '''
        data_list = []
        q = Q('multi_match', query=words, fields=['name', 'content']) # 构造q语句
        self.search = self.search.query('bool', must=[q])
        self.search = self.search[s_n:e_n] 
        response = self.search.execute()
        for hit in response['hits']['hits']:
            data_dict = {}
            data_dict['id'] = hit['_id']
            data_dict['name'] = hit['_source']['name']
            data_dict['age'] = hit['_source']['age']
            data_dict['gender'] = hit['_source']['gender']
            data_dict['content'] = hit['_source']['content']
            data_dict['user_id'] = hit['_source']['user_id']
            data_list.append(data_dict)
        return data_list

    def update(self, item:dict):
        '''通过id更新数据
        :param item: 字典格式，对应model
        :return: 返回更新过后的数据
        '''
        item = self.index.get(item['user_id'])
        item.update(
            name=item['name'],
            age=item['age'],
            gender=item['gender'],
            content=item['content'],
        )
        return item

if __name__ == "__main__":
    DemoInitAPI()
    # 添加

    # 事例。
    # item = {}
    # item['name'] = 'demo'
    # item['user_id'] = '1'
    # item['age'] = '1'
    # item['gender'] = '男'
    # item['content'] = '缘之空'
    # # 实例 api
    # api = DemoESAPI('teacher', DemoTeacherIndex())
    # api.save(item)

    # 删除
    api = DemoESAPI('teacher', DemoTeacherIndex())
    api.delete_all()

    # 通过id找数据
    # item = api.get_data_by_id(1)
    # print(item.name)
    
    # 通过id删除数据
    # api.delete(1)

    # 获取全部数据
    # items = api.get_data_all()
    # print(items)

    # 通过关键词得到数据
    # items = api.get_data_by_words('峰', number=20)
    # print(items)
    # print(len(items))

    # 通过字段找数据
    # item = {'name':'李森'}
    # item = api.get_data_by_field('user_id', '1')
    # print(item)

    # 更新数据
    # 懒得测试了。

    pass