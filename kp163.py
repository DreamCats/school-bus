# __author__: Mai feng
# __file_name__: kp.py
# __time__: 2019:01:17:11:22

import requests, re
from pyquery import PyQuery as pq

class Kp(object):
    '''163kpAPI
    '''
    def __init__(self, keyword):
        # mkan 搜索接口网址
        self.url = 'https://www.163kp.com/index.php?m=vod-search'
        # mkan 根网址
        self.base_url = 'https://www.163kp.com'
        # 初始化 session
        self.s = requests.session()
        # 用户关键词
        self.keyword = keyword
        # 表单
        self.form = {'wd':keyword}
        # thunder 正则
        self.re_thunder = '.*?var GvodUrls1 = "(.*?)";echoDown.*?'


    def get_items_list(self):
        '''获取资源名称列表
        :return: 返回资源列表
        '''
        try:
            res_items = self.s.post(url=self.url, data=self.form)
            if res_items.status_code == 200:                
                doc = pq(res_items.text)
                items = doc('.item .content').items() # .item .content
                item_list = [] 
                for item in items:
                    url = self.base_url + item('.head h3 a').attr('href')
                    title = item('.head h3 a').text()
                    # print(url+'\n',title)
                    item_list.append({'url':url, 'title':title})
                if len(item_list) == 0:
                    print('163kp无能为力，163kp没有该资源')
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
                kp_res = []
                for item in item_list:
                    res_video = self.s.get(url=item['url'])
                    if res_video.status_code == 200:
                        doc = pq(res_video.text)
                        video_items = doc('.playlistlink-1 > li').items()
                        thunder_items = re.findall(self.re_thunder, str(doc('#downlist1')), re.S)
                        down_item_list = []
                        for video_item in video_items:                            
                            down_url = self.base_url + video_item('a').attr('href')
                            title = video_item('a').text()
                            down_item_list.append({'title':title, 'down_url':down_url})
                        for thunder_item in thunder_items:
                            # print(thunder_item)
                            title = thunder_item.split('$')[0]
                            down_url = thunder_item.split('$')[1]
                            down_item_list.append({'title':title, 'thunder_url': down_url})
                    else:
                        return None
                    kp_res.append({'video_name': item['title'], 'items':down_item_list})
                return kp_res
            except Exception as e:
                print('get_video_urls->error', e)
        else:
            return None

    def save(self, kp_res):
        '''将数据保存到根目录下的json文件中
        :param kp_res: 视频资源所有列表
        '''
        if kp_res:
            with open('kp_' + self.keyword + '.json', 'w') as f:
                f.write(str(kp_res))
                print('kp文件生成完毕...')
        else:
            return None

    def start(self):
        '''开始流程
        '''
        item_list = self.get_items_list()
        # print(item_list)
        kp_res = self.get_video_urls(item_list)
        # print(kp_res)
        self.save(kp_res)
        pass


if __name__ == "__main__":
    user_cmd = input('请输入电视剧或者电影的名字?......\n或者键入q之后回车结束......\n')
    if user_cmd != 'q':
        kp = Kp(user_cmd)
        kp.start()
    elif user_cmd == 'q':
        exit()
    else:
        print('请重新运行...')
    # kp = Kp('天降之物')
    # kp = Kp('无名之辈')
    # kp = Kp('nonono')
    # kp.start()
    pass