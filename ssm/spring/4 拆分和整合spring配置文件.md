## 拆分

### 按模块拆分

用户模块 applicationContext_users.xml

商品模块 applicationContext_goods.xml

订单模块 applicationContext_orders.xml

每个xml文件中都包含相应的xxxController,xxxService,xxxDao的对象的创建。

### 按层级拆分

controller：applicationContext_controller.xml

service：applicationContext_serivce.xml

repository：applicationContext_dao.xml

controller层包含所有controller的创建。。。

## 整合

通过import整合

单个整合

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <import resource="SpringContext_Controller.xml"/>
    <import resource="SpringContext_Mapper.xml"/>
    <import resource="SpringContext_Service.xml"/>
</beans>
```

通配符整合:总的配置文件的名称不能在通配符里面，否则会循环递归

total.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <import resource="SpringContext_*.xml"/>
</beans>
```



