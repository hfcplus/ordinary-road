问题：浏览器本身只支持get，post，不支持put，delete

实现步骤

1. 表单或者ajax的请求方式必须是post

2. 加入一个参数，名称为_method,值为put或者delete，表示模拟的请求方式

3. application核心配置文件，进行配置

   ```yaml
   spring:
     mvc:
       hiddenmethod:
         filter:
           enabled: true #开启filter，支持模拟的put
   ```

   