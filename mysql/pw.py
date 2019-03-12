# __author__: Mai feng
# __file_name__: pe.py
# __time__: 2019:03:12:10:52

# 利用peewee操作mysql
from peewee import *

from datetime import date

database = MySQLDatabase(
    'pw',
    user='root',
    password='123',
    host='localhost',
    port=3306
)

# 定义Person模型
class Person(Model):
    name = CharField()
    birthday = DateField()
    is_relative = BooleanField()

    class Meta:
        database = database

# 创建Person
# Person.create_table()

# 添加一条数据
# p = Person(
#     name='Mai',
#     birthday=date(1994, 8, 22),
#     is_relative=True
# )
# p.save()
