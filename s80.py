# __author__: Mai feng
# __file_name__: s80.py
# __time__: 2019:08:30:15:20
import requests
from pyquery import PyQuery as pq
class S80(object):
    '''80s api
    '''
    def __init__(self, keyword):
        # 80s 搜索接口网址
        self.url = 'https://www.80s.tw/search'
        # 80s 根网址
        self.base_url = 'https://www.80s.tw'
        # 初始化 session
        self.s = requests.session()
        # 用户关键词
        self.keyword = keyword
        # 表单
        self.form = {'keyword': keyword}


    def get_items_list(self):
        '''获取资源名称列表
        :return: 返回资源列表
        '''
        try:
            res_items = self.s.post(
                url=self.url,
                data=self.form,
            )
            if res_items.status_code == 200:
                doc = pq(res_items.text)
                items = doc('#block3 > ul > li').items() # #block3 > ul > li
                item_list = [] 
                for item in items:
                    url = self.base_url + item('a').attr('href')
                    title = item('a').text()
                    # print(url+'\n',title)
                    item_list.append({'url':url, 'title':title})
                if len(item_list) == 0:
                    print('80s无能为力，80s没有该资源')
                    return None
                else:
                    return item_list
            else:
                return None
        except Exception as e:
            print('get_items_list->error', e)
            return None

    def get_video_urls(self, item_list):
        '''获取url链接
        :param item_list: 视频资源名称列表
        :return: 返回关键词所有资源
        '''
        if item_list:
            try:
                s80_res = []
                for item in item_list:
                    res_video = self.s.get(url=item['url'])
                    if res_video.status_code == 200:
                        doc = pq(res_video.text)
                        video_items = doc('.dlname').items()
                        down_item_list = []
                        for video_item in video_items:
                            down_url = video_item('span a').attr('href')
                            title = video_item('span a').text()
                            down_item_list.append({'title':title, 'down_url':down_url})
                    else:
                        return None
                    s80_res.append({'video_name': item['title'], 'items':down_item_list})
                return s80_res
            except Exception as e:
                print('get_video_urls->error', e)
        else:
            return None

    def save(self, s80_res):
        '''将数据保存到根目录下的json文件中
        :param s80_res: 视频资源所有列表
        '''
        if s80_res:
            with open('s80_' + self.keyword + '.json', 'w') as f:
                f.write(str(s80_res))
                print('s80文件生成完毕...')
        else:
            return None

    def start(self):
        '''开始流程
        '''
        item_list = self.get_items_list()
        s80_res = self.get_video_urls(item_list)
        self.save(s80_res)
        pass


if __name__ == "__main__":
    user_cmd = input('请输入电视剧或者电影的名字?......\n或者键入q之后回车结束......\n')
    if user_cmd != 'q':
        s80 = S80(user_cmd)
        s80.start()
    elif user_cmd == 'q':
        exit()
    else:
        print('请重新运行...')
    # s80 = S80('斗罗大陆')
    # s80 = S80('无名之辈')
    # s80 = S80('nonono')
    # s80.start()
    pass