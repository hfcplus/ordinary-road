## 基础接口

```java
package plus.hf.service;

public interface Service {
    void sing();
}
```

## 实现类

```java
package plus.hf.service.impl;

import plus.hf.service.Service;

public class HuGe implements Service {
    @Override
    public void sing() {
        System.out.println("我是胡歌，我在唱神话！");
    }
}

```

## jdk动态代理

```java
package plus.hf.factory;

import plus.hf.service.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxyFactory {
    // 类的成员变量为接口
    Service service;
	
    // 类的构造函数参数为接口
    public JDKDynamicProxyFactory(Service service) {
        this.service = service;
    }

    // 重点
    public Object getFactory() {
        return Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("我是代理工场，我可以获得愈多代理");
                        System.out.println("我的代理可以怎强目标对象");
                        System.out.println("我可以预定场地");
                        Object invoke = method.invoke(service, args);
                        System.out.println("结束目标对象");
                        return invoke;
                    }
                });
    }
}

```

## 测试动态代理

```java
package plus.hf;

import org.junit.Test;
import plus.hf.factory.JDKDynamicProxyFactory;
import plus.hf.service.Service;
import plus.hf.service.impl.StaticAgent;
import plus.hf.service.impl.HuGe;

public class MyTest {
    @Test
    public void JDKDynamicProxyTest(){
        ZhangJie zhangJie = new ZhangJie();
        JDKDynamicProxyFactory jdkDynamicProxyFactory = new JDKDynamicProxyFactory(zhangJie);
        Service service = (Service)jdkDynamicProxyFactory.getFactory();
        service.sing();
    }
}
```

