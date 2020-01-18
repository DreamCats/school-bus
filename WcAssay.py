import requests, time
from tqdm import tqdm

requests.packages.urllib3.disable_warnings()

def save_file(author, data):
    '''存储文件
    '''
    with open (f'{author}.json', 'a') as f:
        f.write(data + '\n')

def timestamp_to_time(timestamp):
    '''时间戳转日期'''
    timeArray = time.localtime(timestamp)
    otherStyleTime = time.strftime("%Y--%m--%d %H:%M:%S", timeArray)
    return otherStyleTime

def parse(url, headers, author):
    s = requests.session()
    res = s.get(url, headers=headers, verify=False)
    res_ok = res.json()['ret']
    if res_ok == 0:
        can_msg_continue = res.json()['can_msg_continue']
        if can_msg_continue == 1:
            general_msg_list = res.json()['general_msg_list']
            msg_data = eval(general_msg_list)['list']
            if msg_data == [] or len(msg_data) == 0:
                print('list is null， 检查作者url链接')
                return -3
            datas = []
            for data in msg_data:
                # print(data)
                try:
                    data_time = timestamp_to_time(data['comm_msg_info']['datetime'])
                    title = data['app_msg_ext_info']['title']
                    url = data['app_msg_ext_info']['content_url']
                except Exception as e:
                    print('it is not essay!, continue...\n', e)
                    continue
                datas.append({
                    'time':data_time,
                    'title':title,
                    'url':url
                })
                data = f'data_time:{data_time} title:{title} url:{url}'
                save_file(author, data)
        else :
            print('爬虫已经结束')
    else:
        print(f'请求错误,重新获取新的带时间戳的url... \n {res.json()}')
        return '-3'



if __name__ == "__main__":
    
    # headers
    headers = {
        'Cookie': 'devicetype=iPhoneiOS13.2.3; lang=zh_CN; pass_ticket=ryOxuSwLCeuw8olDueApERM1eP3lJs1D9NAFnAukAgAqaUIsaSq8FKcXMFVXOmP; version=17000a26; wap_sid2=CLSjjdUGElxzQUJBWkRFVEVFTGpYRmlIWDdUd1FpS0R0UzdIRGNqSmZKbXRRUUZYSE9nSXNtMXRWUHRTVDZ1VGpNWXJ2ZmluaXV2U25uUm1DelR3bEZQOVYzTWZueFFFQUFBfjDpgIrxBTgNQJVO; wxuin=1789088180',
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) MicroMessenger/2.3.29(0x12031d10) MacWechat Chrome/39.0.2171.95 Safari/537.36 NetType/WIFI WindowsWechat MicroMessenger/2.3.29(0x12031d10) MacWechat Chrome/39.0.2171.95 Safari/537.36 NetType/WIFI WindowsWechat'
    }
    offset = 0
    count = 10
    page = 30
    author = 'qiao'
    # 乔戈里的微信公众号链接
    for ii in range(page):
        url = f'https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MzI5MzYzMDAwNw==&f=json&offset={offset}&count=10&is_ok=1&scene=124&uin=MTc4OTA4ODE4MA%3D%3D&key=3921ae179239b6e0dce119277cf0123ec972b525b5245cb37d57e0d6ef6a4a0c9ddec399ce5fd1b3627caf762c266df0fa6f373a8edd39f0fce35a8c896db766bcfeb55a16f9415757547f09467ffb14&pass_ticket=ryOxuSwLCeuw8o%2BlDueApERM1eP3lJs1D9NAFnAukAgAqaUIsaSq8FKcXMFVXOmP&wxtoken=&appmsg_token=1044_xhI0KVivTxCtyLFDPFNyNi7Lg_81ukFjs511OA~~&x5=0&f=json'
        print(f'第{ii}页, offset:{offset}, url:{url}')
        res = parse(url, headers, author)
        if res == -3:
            break
        offset += count
        time.sleep(15)

