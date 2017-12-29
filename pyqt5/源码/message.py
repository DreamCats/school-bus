'''
@author:Mai feng

'''
from PyQt5.QtWidgets import QWidget, QMessageBox


class Message(QWidget):
    def __init__(self):
        super(Message,self).__init__()

        self.initUi()

    def initUi(self):
        self.setGeometry(1000, 600, 600, 600)


    def msg_notify(self,item,msg):
        reply = QMessageBox()
        result = reply.information(
            self,
            item,
            msg,
            QMessageBox.Yes | QMessageBox.No
        )

        if result == QMessageBox.Yes:
            return True
        else:
            return False
