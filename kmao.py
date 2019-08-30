# __author__: Mai feng
# __file_name__: kmao.py
# __time__: 2019:08:16:13:33
import requests
from pyquery import PyQuery as pq


class Kmao(object):
    '''4K屋API
    '''
    def __init__(self, keyword):
        # mkan 搜索接口网址
        self.url = 'http://www.kkkkmao.com/index.php?s=vod-search-wd-{keyword}-1-ajax'.format(keyword=keyword)
        # mkan 根网址
        self.base_url = 'http://www.kkkkmao.com'
        # 初始化 session
        self.s = requests.session()
        # 用户关键词
        self.keyword = keyword
        # base_headers
        self.headers = {'X-Requested-With':'XMLHttpRequest'}

    def get_items_list(self):
        '''获取资源名称列表
        :return: 返回资源列表
        '''
        try:
            res_items = self.s.get(url=self.url, headers=self.headers)
            if res_items.status_code == 200:
                data = res_items.json()['data']['ajaxtxt']
                if data:
                    doc = pq(data)
                    items = doc('.play-txt > h5').items() # .play-txt
                    item_list = [] 
                    for item in items:
                        url = self.base_url + item('a').attr('href')
                        title = item('a').text()
                        # print(url+'\n',title)
                        item_list.append({'url':url, 'title':title})
                    if len(item_list) == 0:
                        print('4K屋无能为力，4K屋没有该资源')
                        return None
                    else:
                        return item_list
                else:
                    return None
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
                kmao_res = []
                for item in item_list:
                    res_video = self.s.get(url=item['url'])
                    res_video.encoding = 'utf-8'
                    if res_video.status_code == 200:
                        doc = pq(res_video.text)
                        video_items = doc('.play-list > a').items()
                        down_item_list = []
                        for video_item in video_items:
                            down_url = self.base_url + video_item.attr('href')
                            title = video_item.text()
                            down_item_list.append({'title':title, 'down_url':down_url})
                    else:
                        return None
                    kmao_res.append({'video_name': item['title'], 'items':down_item_list})
                return kmao_res
            except Exception as e:
                print('get_video_urls->error', e)
        else:
            return None

    def save(self, kmao_res):
        '''将数据保存到根目录下的json文件中
        :param kmao_res: 视频资源所有列表
        '''
        if kmao_res:
            with open('kmao_' + self.keyword + '.json', 'w') as f:
                f.write(str(kmao_res))
                print('kmao文件生成完毕...')
        else:
            return None

    def start(self):
        '''开始流程
        '''
        item_list = self.get_items_list()
        # print(item_list)
        kmao_res = self.get_video_urls(item_list)
        # print(kmao_res)
        self.save(kmao_res)
        pass

if __name__ == "__main__":
    user_cmd = input('请输入电视剧或者电影的名字?......\n或者键入q之后回车结束......\n')
    if user_cmd != 'q':
        kmao = Kmao(user_cmd)
        kmao.start()
    elif user_cmd == 'q':
        exit()
    else:
        print('请重新运行...')
    # kmao = Kmao('天降之物')
    # kmao = Kmao('无名之辈')
    # kmao = Kmao('nonono')
    # kmao.start()
    # pass