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
        table_name = 'person'

# 创建Person
# Person.create_table()

# 添加一条数据
# p = Person(
#     name='Mai',
#     birthday=date(1994, 8, 22),
#     is_relative=True
# )
# p.save()

p = Person()
# p.name = 'feng'
# p.birthday = date(2000, 12, 1)
# p.is_relative = False
# p.save()

# 删除
# Person.delete().where(Person.name == 'feng').execute()
# p = Person.get(Person.name == 'feng')
# p.delete_instance()

# 改
# q = Person.update({Person.birthday: date(1983, 12, 21)}).where(Person.name == 'mai')
# q.execute()

# q = Person.get(Person.name == 'Mai')
# q = q.update(
#     {
#         Person.birthday:date(2019, 1, 1),
#     }
# ).execute()

# 查询单条数据
# p = Person.get(Person.name == 'Mai')
# print(p.id, p.name, p.birthday, p.is_relative)

# 查询多条数据

# persons = Person.select().where(Person.is_relative == True)
# for p in persons:
#     print(p.id, p.name, p.birthday, p.is_relative)