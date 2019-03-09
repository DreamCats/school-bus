# EsDemo
## Info

**Author**: *DreamCats* </br>
**Date**: *2019.1.14.22.00*
**Describe**:*对Elasticsearch的python操作，简单写个api*


-------
## 操作
1. 安装Elasticsearch省略，以及安装ik插件省略
2. 对DemoInitAPI实例且初始化
3. 代码如下

    ```python
    if __name__ == "__main__":
        DemoInitAPI()
    ```
4. 添加操作，代码如下：

    ```python
    if __name__ == "__main__":
        item = {}
        item['name'] = 'demo'
        item['user_id'] = '1'
        item['age'] = '1'
        item['gender'] = '男'
        item['content'] = '缘之空'
        # 实例 api
        api = DemoESAPI('teacher', DemoTeacherIndex())
        api.save(item)
    ```
5. 删除操作，代码如下

    ```python
    api = DemoESAPI('teacher', DemoTeacherIndex())
    api.delete_all()
    ```
6. 通过id找数据

    ```python
    api = DemoESAPI('teacher', DemoTeacherIndex())
    item = api.get_data_by_id(1)
    print(item)
    ```
7. 通过id删除数据

    ```python
    api = DemoESAPI('teacher', DemoTeacherIndex())
    api.delete(1)
    ```
8. 获取全部数据

    ```python
    api = DemoESAPI('teacher', DemoTeacherIndex())
    items = api.get_data_all()
    print(items)
    ```
9. 通过关键词得到数据

    ```python
    api = DemoESAPI('teacher', DemoTeacherIndex())
    items = api.get_data_by_words('峰', number=20)
    print(items)
    ```
10. 通过字段找数据

    ```python
    api = DemoESAPI('teacher', DemoTeacherIndex())
    item = {'name':'李森'}
    item = api.get_data_by_field('user_id', '1')
    print(item)
    ```
11. 更新数据, 你懂的    