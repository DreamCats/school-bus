#_*_coding:utf-8_*_


from PyQt5.QtCore import QThread,Qt,pyqtSignal
from PyQt5.QtWidgets import QWidget,QLabel,QApplication
import sys
import time

class MyWindow(QWidget):

    def __init__(self):
        super().__init__()
        self.setWindowTitle("demo")
        self.resize(400,200)
        self.lable =QLabel("这是一个标签",self)
        self.lable.move(150,100)
        self.num = Num()
        self.sum = Sum()    #实例化Sum类
        self.sum.sinOut.connect(self.num.setNum)  #将信号连接至printNum函数
        self.num.start()
        self.sum.start()    #开启线程
        self.show()
    def printNum(self,num):
        print(num)

class Sum(QThread):
    sinOut = pyqtSignal(int)
    def __init__(self):
        super().__init__()
        self.m = 0

    def run(self):
        while self.m < 100:
            self.m+=1
            self.sinOut.emit(self.m)    #反馈信号出去
            time.sleep(1)


class Num(QThread):
    def __init__(self):
        super().__init__()
        self.Num = 0
        self.flag = True

    def setNum(self,state):
        self.flag = state

    def run(self):
        while True:
            if self.flag == True:
                print(self.Num)
            self.Num+=1
            time.sleep(1)

if __name__ == '__main__':
    app =QApplication(sys.argv)
    win = MyWindow()
    sys.exit(app.exec())