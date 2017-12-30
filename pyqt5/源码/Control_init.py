/*
 * @Author: Hy-Maifeng 
 * @Date: 2017-12-30 15:41:30 
 * @Last Modified by: Hy-Maifeng
 * @Last Modified time: 2017-12-30 16:47:37
 */
 # 导包初始化
from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from PyQt5.QtGui import *

class Ui_MainWindow(QMainWindow):
    def __init__(self):
        super(Ui_MainWindow, self).__init__()
        self.setupUi(self)
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(1300, 800) # x,y
        MainWindow.setWindowIcon(QIcon('./img/icon.jpg'))

        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")

        # pushButton
        self.pushButton = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton.setGeometry(QtCore.QRect(120, 100, 200, 100)) # x,y,x,y
        self.pushButton.setObjectName("pushButton")
        # radioButton
        self.radioButton = QtWidgets.QRadioButton(self.centralwidget)
        self.radioButton.setGeometry(QtCore.QRect(630, 110, 174, 28))
        self.radioButton.setObjectName("radioButton")
        # checkBox
        self.checkBox = QtWidgets.QCheckBox(self.centralwidget)
        self.checkBox.setGeometry(QtCore.QRect(930, 110, 138, 28))
        self.checkBox.setObjectName("checkBox")
        # commandLinkButton
        self.commandLinkButton = QtWidgets.QCommandLinkButton(self.centralwidget)
        self.commandLinkButton.setGeometry(QtCore.QRect(160, 260, 337, 65))
        self.commandLinkButton.setObjectName("commandLinkButton")

        # comboBox
        self.comboBox = QtWidgets.QComboBox(self.centralwidget)
        self.comboBox.setGeometry(QtCore.QRect(30, 20, 128, 30))
        self.comboBox.setObjectName("comboBox")
        # self.comboBox.addItem("") 添加条目,首先要添加这个
        # self.comboBox.addItem("")
        # self.comboBox.addItem("")
        # _translate = QtCore.QCoreApplication.translate
        # MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        # self.comboBox.setItemText(0, _translate("MainWindow", "1"))
        # self.comboBox.setItemText(1, _translate("MainWindow", "2"))
        # self.comboBox.setItemText(2, _translate("MainWindow", "3"))
        # lineEdit
        self.lineEdit = QtWidgets.QLineEdit(self.centralwidget)
        self.lineEdit.setGeometry(QtCore.QRect(40, 70, 113, 31))
        self.lineEdit.setObjectName("lineEdit")
        # textEdit
        self.textEdit = QtWidgets.QTextEdit(self.centralwidget)
        self.textEdit.setGeometry(QtCore.QRect(210, 20, 140, 140))
        self.textEdit.setObjectName("textEdit")
        # plainTextEdit
        self.plainTextEdit = QtWidgets.QPlainTextEdit(self.centralwidget)
        self.plainTextEdit.setGeometry(QtCore.QRect(390, 20, 140, 140))
        self.plainTextEdit.setObjectName("plainTextEdit")
        # label
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(20, 30, 108, 24))
        self.label.setObjectName("label")
        # progressBar
        self.progressBar = QtWidgets.QProgressBar(self.centralwidget)
        self.progressBar.setGeometry(QtCore.QRect(200, 30, 132, 26))
        self.progressBar.setProperty("value", 24)
        self.progressBar.setObjectName("progressBar")
        # listWidget
        self.listWidget = QtWidgets.QListWidget(self.centralwidget)
        self.listWidget.setGeometry(QtCore.QRect(30, 80, 256, 192))
        self.listWidget.setObjectName("listWidget")
        # item = QtWidgets.QListWidgetItem()
        # self.listWidget.addItem(item)
        # item = QtWidgets.QListWidgetItem()
        # self.listWidget.addItem(item)
        # item = QtWidgets.QListWidgetItem()
        # self.listWidget.addItem(item)
        # __sortingEnabled = self.listWidget.isSortingEnabled()
        # self.listWidget.setSortingEnabled(False)
        # item = self.listWidget.item(0)
        # item.setText(_translate("MainWindow", "1"))
        # item = self.listWidget.item(1)
        # item.setText(_translate("MainWindow", "2"))
        # item = self.listWidget.item(2)
        # item.setText(_translate("MainWindow", "3"))
        # self.listWidget.setSortingEnabled(__sortingEnabled)

        # treeWidget
        self.treeWidget = QtWidgets.QTreeWidget(self.centralwidget)
        self.treeWidget.setGeometry(QtCore.QRect(30, 280, 431, 151))
        self.treeWidget.setObjectName("treeWidget")
        # self.treeWidget.headerItem().setText(0, _translate("MainWindow", "1买峰"))
        # self.treeWidget.headerItem().setText(1, _translate("MainWindow", "2"))
        # self.treeWidget.headerItem().setText(2, _translate("MainWindow", "3"))
        
        # tableWidget
        self.tableWidget = QtWidgets.QTableWidget(self.centralwidget)
        self.tableWidget.setGeometry(QtCore.QRect(20, 450, 461, 231))
        self.tableWidget.setObjectName("tableWidget")
        self.tableWidget.setColumnCount(2)
        self.tableWidget.setRowCount(2)
        # item = QtWidgets.QTableWidgetItem()
        # self.tableWidget.setVerticalHeaderItem(0, item)
        # item = QtWidgets.QTableWidgetItem()
        # self.tableWidget.setVerticalHeaderItem(1, item)
        # item = QtWidgets.QTableWidgetItem()
        # self.tableWidget.setHorizontalHeaderItem(0, item)
        # item = QtWidgets.QTableWidgetItem()
        # self.tableWidget.setHorizontalHeaderItem(1, item)
        # item = self.tableWidget.verticalHeaderItem(0)
        # item.setText(_translate("MainWindow", "1"))
        # item = self.tableWidget.verticalHeaderItem(1)
        # item.setText(_translate("MainWindow", "2"))
        # item = self.tableWidget.horizontalHeaderItem(0)
        # item.setText(_translate("MainWindow", "1"))
        # item = self.tableWidget.horizontalHeaderItem(1)
        # item.setText(_translate("MainWindow", "2"))
