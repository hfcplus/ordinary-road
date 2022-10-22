## 方法一通过xml定义

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--添加包扫描-->
    <context:component-scan base-package="plus.hf.service"/>

    <!--添加事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--配置数据源-->
        <property name="dataSource"  ref="dataSource"/>
    </bean>

    <!--添加事务切面-->
    <tx:advice id="myAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!--查询-->
            <tx:method name="*select*" read-only="true"/>
            <tx:method name="*find*" read-only="true"/>
            <tx:method name="*search*" read-only="true"/>
            <tx:method name="*query*" read-only="true"/>
            <tx:method name="*get*" read-only="true"/>
            <!--增删给改查-->
            <tx:method name="insert*" propagation="REQUIRED"/>
            <tx:method name="add*" propagation="REQUIRED"/>
            <tx:method name="save*" propagation="REQUIRED" no-rollback-for="ArithmeticException"/>
            <tx:method name="set*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="modify*" propagation="REQUIRED"/>
            <tx:method name="change*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="remove*" propagation="REQUIRED"/>
            <tx:method name="clear*" propagation="REQUIRED"/>
            <tx:method name="empty*" propagation="REQUIRED"/>
            <!--其他操作支持事务-->
            <tx:method name="*" propagation="SUPPORTS"/>
        </tx:attributes>
    </tx:advice>
    <!--绑定切面和切入点-->
    <aop:config>
        <aop:pointcut id="myCut" expression="execution(* plus.hf.service.impl.*.*(..))"/>
        <aop:advisor advice-ref="myAdvice" pointcut-ref="myCut"/>
    </aop:config>
</beans>
```

## 方法二，java

```java
package plus.hf.s01;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author : HFC
 * @date : 2022/4/1 9:07
 * @description :
 */

@Aspect
@Component
public class MyAspect {
    /**
     * 1. 访问权限  public
     * 2. 返回值 void
     * "execution(public String plus.hf.s01.SomeServiceImpl.doSome(String, Integer))"
     * "execution(public * plus.hf.*.*(..))"
     * public修饰符可以省略但是不能用*代替，*是返回值类型，包下面的类，类的方法，方法的参数..
     */

    @Before(value = "execution(public String plus.hf.s01.SomeServiceImpl.doSome(String, Integer))")
    public void before(){
        System.out.println("aspectj实现切面前置");
    }

    @AfterReturning(value = "execution(public String plus.hf.s01.SomeServiceImpl.doSome(String, Integer))")
    public void after(){
        System.out.println("aspectj实现切面后置");
    }

    @AfterReturning(value = "execution(public * plus.hf.s01.SomeServiceImpl.*(..))", returning = "object")
    public void afterStudent(Object object){
        System.out.println("aspectj实现切面后置");
        if (object != null) {
            if (object instanceof Student) {
                ((Student) object).setName("hfc");
            }
        }
    }
    
        @Around(value = "execution(* plus.hf.s02.*.*(..))")
    public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length >= 1) {
//            String name = (String) args[0];
//            if ("hfc".equals(name)) {
                System.out.println("环绕通知前切功能-----");
                Object proceed = pjp.proceed(pjp.getArgs());
                System.out.println("环绕通知后切功能-----");
//            }
        }
        return null;
    }
}

```

