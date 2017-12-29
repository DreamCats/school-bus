# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ui_progress.ui'
#
# Created by: PyQt5 UI code generator 5.9
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QWidget

from ui.message import Message
from ui.ui_handler.handler_bar import Download


class Ui_progress_bar(QWidget):
    def __init__(self):
        super(Ui_progress_bar, self).__init__()
        self.setupUi(self)



    def setupUi(self, progress_bar):
        progress_bar.setObjectName("progress_bar")
        progress_bar.resize(691, 244)
        self.progressBar = QtWidgets.QProgressBar(progress_bar)
        self.progressBar.setGeometry(QtCore.QRect(120, 90, 451, 26))
        self.progressBar.setProperty("value", 0)
        self.progressBar.setMaximum(1000)
        self.progressBar.setObjectName("progressBar")
        self.bt_download = QtWidgets.QPushButton(progress_bar)
        self.bt_download.setGeometry(QtCore.QRect(120, 160, 150, 46))
        self.bt_download.setObjectName("bt_download")
        self.bt_exit_2 = QtWidgets.QPushButton(progress_bar)
        self.bt_exit_2.setGeometry(QtCore.QRect(400, 160, 150, 46))
        self.bt_exit_2.setObjectName("bt_exit_2")

        self.retranslateUi(progress_bar)
        self.bt_exit_2.clicked.connect(progress_bar.close)
        self.bt_download.clicked.connect(self.event_downlaod)
        QtCore.QMetaObject.connectSlotsByName(progress_bar)

    def retranslateUi(self, progress_bar):
        _translate = QtCore.QCoreApplication.translate
        progress_bar.setWindowTitle(_translate("progress_bar", "Form"))
        self.bt_download.setText(_translate("progress_bar", "下载"))
        self.bt_exit_2.setText(_translate("progress_bar", "退出"))

    '''
    @author:Maifeng
    @func:更新ui的值
    @param:
    @return:
    '''
    def set_ui_value(self,value:dict):
        self.web = value["web"]
        self.dir = value["dir"]
        self.num = value["num"]
        print(self.web,self.dir,self.num)
        return self

    def set_bar_value(self,count:int):
        print(count)
        self.progressBar.setValue(count)

    '''
    @author:Maifeng
    @func:按钮点击
    @param:
    @return:
    '''
    def event_downlaod(self):
        message = Message()
        message.msg_notify('通知','是否要下载')
        # 实例化 线程
        self.download_thread = Download(self.web,self.dir,self.num)
        # 建立信号溜
        self.set_download_signal()
        # 开启线程
        self.download_thread.start()


    '''
    @author:Maifeng
    @func:信号流
    @param:
    @return:
    '''
    def set_download_signal(self):
        self.download_thread.feedback_bar_value.connect(self.set_bar_value)



