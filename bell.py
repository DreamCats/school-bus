import requests
from pyquery import PyQuery as pq
import json
import os
from multiprocessing import Pool
from time import time
class Config:
    data_dir = './datas/'
    chinese = None
    chinese_page = 10
    english = None
    english_page = 10
    chinese_url = 'http://www.haolingsheng.com/jsonp/tag.asp?callback=callback&cid={id}&page={page}'
    song_url = 'http://www.haolingsheng.com/lingsheng/{id}.htm'

class Spider():
    def __init__(self):
        self.s = requests.session()
        try:
            os.mkdir(Config.data_dir)
        except:
            pass
    
    def get_list(self, url):
        res = self.s.get(url)
        if res.status_code == 200:
            datas = res.text.split('callback(')[1].split(')')[0]
            datas = json.loads(datas).get('result')
            return datas
        else:
            return None

    def get_video(self,datas, cf='zh'):
        if datas:
            for index, data in enumerate(datas):
                print('第{0}个-歌名:{1}-time:{2}s'.format(
                index, data['topic'], data['time']))
                url = Config.song_url.format(id=data['shorturl'])
                res = self.s.get(url)
                if res.status_code == 200:
                    doc = pq(res.text)
                    down_url = doc('.download a').attr('href')
                    content = self.down_video(down_url)
                    self.save_video(content, data['topic'], cf=cf)
                else:
                    return None

        else:
            return None

    def down_video(self, url):
        if url:
            res = self.s.get(url)
            if res.status_code == 200:
                return res.content
            else:
                return None
        else:
            return None

    def save_video(self, content, name, cf='zh'):
        if content:
            with open(Config.data_dir + cf + '-' + name + '.wav', 'wb') as f:
                f.write(content)
                print('download:{0} is ok'.format(name))
        else:
            return None

    def run(self):
        for ii in range(1, Config.chinese_page + 1):
            start_time = time()
            url = Config.chinese_url.format(id=7, page=ii)
            p = Pool(2)
            datas = self.get_list(url)
            patch = len(datas) // 2
            for jj in range(2):
                if jj < 1:
                    sdatas = datas[jj*patch: (jj+1)*patch]
                else:
                    sdatas = datas[jj*patch:]
                print("task %s sdatas length: %d" %(jj, len(sdatas)))
                p.apply_async(self.get_video, args=(sdatas,'zh'))
            print('Waiting for all subprocesses done...')
            p.close()
            p.join()
            print('page:{0} | time:{1:.3f}s'.format(ii, time()-start_time))
            # self.get_video(datas,cf='zh')
            exit()

    
if __name__ == "__main__":
    spider = Spider()
    spider.run()
