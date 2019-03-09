# __author__: Mai feng
# __file_name__: rand_str.py
# __time__: 2019:01:22:15:45
import random
class RandStr(object):
    def __init__(self, length):
        '''初始化
        :param length: 字符串长度
        '''
        self.str = 'zxcvbnmlkjhgfdsaqwertyuiopPOIUYTREWQASDFGHJKLMNBVCXZ1234567890'
        self.length = length

    def get_rand_str(self):
        '''返回一定长度的随机字符串
        :return: 和功能说明一样
        '''
        random.seed()
        number = []
        for i in range(self.length):
            number.append(random.choice(self.str))
        res = ''.join(number)
        return res


if __name__ == "__main__":
    rand_str = RandStr(length=5)
    res = rand_str.get_rand_str()
    print(res)

