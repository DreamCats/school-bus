import redis

class Myredis(object):
    def __init__(self,localhost,port):
        self.localhost = localhost
        self.port      = port
    def set_connect(self):
        try:
            pool = redis.ConnectionPool(host=self.localhost,port=self.port)
            self.r = redis.Redis(connection_pool=pool)
            return self.r
        except:
            print('error connect')




'''
test
localhost = 'localhost'
port = 6379
r = Myredis(localhost,port)
test = r.set_connect()

'''


