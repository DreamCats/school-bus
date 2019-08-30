# __author__: Mai feng
# __file_name__: anime.py
# __time__: 2019:08:30:15:01
import requests, os, random
from pyquery import PyQuery as pq
class Anime(object):
    def __init__(self, keyword):
        # 用户的关键词
        self.keyword = keyword
        # base_url 
        self.base_url = 'https://wall.alphacoders.com/'
        # search_url
        self.search_url = 'https://wall.alphacoders.com/search.php'
        # search_params
        self.search_params = {
            'search':keyword,
            'lang':'Chinese',
            'page':'1' # 默认
        }
        # post_url 获取真实链接
        self.post_url = 'https://wall.alphacoders.com/get_download_link.php'
        # 实例 requests.session
        self.s = requests.session()
        # 初始化随机串
        self.rand_words = 'zxcvbnmlkjhgfdsaqwertyuiopPOIUYTREWQASDFGHJKLMNBVCXZ1234567890'
        self.rand_num  = 5

    def is_user_dir(self):
        '''判断在根目录下用户的关键词是否已经存在文件夹
        '''
        if os.path.isdir('{dir}'.format(dir=self.keyword)):
            print(self.keyword+'文件夹已经存在，但没有影响')
        else:
            print('该文件夹不存在，正在创建' + self.keyword + '文件夹')
            os.mkdir(self.keyword)
            print('创建成功...')
        
    def rand_str(self):
        '''随机生成固定的字符串
        :return: 字符串
        '''
        random.seed()
        number = []
        for i in range(self.rand_num):
            number.append(random.choice(self.rand_words))
        _id = ''.join(number)
        return _id

    def get_img_url(self):
        '''获取url链接
        '''
        try:
            res_url = self.s.get(url=self.search_url, params=self.search_params)
            if res_url.status_code == 200:
                doc = pq(res_url.text)
                number = doc('#page_container h1').text()       
                if number:
                    number = number.split('张')[0]
                    temp_page = int(number) % 30 
                    total_page = int(number) // 30
                    if temp_page > 0:
                        total_page = total_page + 1 
                    print('一共搜到了%s张壁纸' %number)
                    items = doc('.boxgrid a').items()
                    for item in items:
                        url = self.base_url + item.attr('href')
                        # 获取真实的下载链接
                        download_url = self.parse_url(url)
                        # 下载
                        self.download(download_url)
                    this_page = int(self.search_params['page'])
                    # 翻页下载。
                    if this_page < total_page:
                        print('第' + str(this_page) + '页下载完毕... \n')
                        self.search_params['page'] = this_page + 1
                        return self.get_img_url()
                    else:
                        print('全部下载完毕...')
                        return None
                else:
                    print('没有相应的动漫壁纸...')
                    return None
            else:
                return None
        except Exception as e:
            print('get_img_url->error:', e)
            return None

    def parse_url(self, url):
        '''解析img_url获取下载链接
        '''
        try:
            res_url = self.s.get(url=url)
            if res_url.status_code == 200:
                doc = pq(res_url.text)
                data_id = doc('.download-button').attr('data-id')
                data_type = doc('.download-button').attr('data-type')
                data_server = doc('.download-button').attr('data-server')
                data_user_id = doc('.download-button').attr('data-user-id')
                post_data = {
                    'wallpaper_id':data_id,
                    'type':data_type,
                    'server':data_server,
                    'user_id':data_user_id
                }
                res_url = self.s.post(url=self.post_url, data=post_data)
                if res_url.status_code == 200:
                    return res_url.text
                else:
                    return None
            else:
                return None
        except Exception as e:
            print('parse_url->error', e)
        pass

    def download(self, url):
        '''下载壁纸
        '''
        if url:
            res = self.s.get(url=url)
            if res.status_code == 200:
                img_name = self.keyword + '_' + self.rand_str() + '.jpg'
                with open(self.keyword + '/{name}'.format(name=img_name), 'wb') as f:
                    f.write(res.content)
                    print('图片' + img_name + '下载成功')
            else:
                return None
        else:
            return None

    def run(self):
        '''run的流程
        '''
        self.is_user_dir()
        self.get_img_url()
   

def main():
    user_cmd = input('请输入壁纸的名字，尽量是某番的名字...\n')
    if user_cmd != 'q':
        anime = Anime(user_cmd)
        anime.run()
    elif user_cmd == 'q':
        exit()
    else:
        main()
if __name__ == "__main__":
    # anime = Anime('约会大作战')
    # anime = Anime('中二病也要谈恋爱')
    # anime = Anime('nonono')
    main()
