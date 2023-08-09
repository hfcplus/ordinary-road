## Spring AOP

动态代理，程序运行时增强，所以切面越多性能越差



## Aspectj

静态代理，编译时增强，在编译阶段就生成AOP代理类，因此也称为编译时增强



## AOP概念

**JoinPoint（链接点）**：被切面织入的具体的方法（被动态代理拦截目标类的方法）

**Pointcut（切入点）**：是一个（组）基于正则表达式的表达式，有点绕，就是说他本身是一个表达式，但是他是基于正则语法的。一个或多个链接点的集合。被final修饰的方法是不能被增强的

**Advice（通知或者增强）**：在选取出来的JoinPoint上要执行的操作、逻辑。

**Target（目标对象）**：被代理的类

**Weaving（织入）**：把切面应用到目标对象来创建新的代理对象的过程。

**Proxy（代理）**：生成的代理对象

**Aspect（切面）**：由pointcut 和advice组成。



## Aspectj

### 切入点表达式

execution(访问权限? 返回值类型 包名类名? 方法名(参数) 抛出异常类型?)

| 符号 |                             意义                             |
| :--: | :----------------------------------------------------------: |
|  ？  |                             可选                             |
|  *   |                        0个或多个字符                         |
|  ..  | 用在参数里代表任意多个参数，用在包名类名后代表当前包及子包路径 |
|  +   | 用在类名后表示当前类以及其子类，用在接口后表示当前接口及其实现类 |

### 通知类型

* 前置通知@Before
* 后置通知@AfterReturning
* 环绕通知@Around
* 最终通知@After

### Spring集成Aspectj

依赖

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aspects</artifactId>
  <version>5.3.16</version>
</dependency>
```

#### 基于注解模式实现

1. 定义接口和实现类(需要被增强的类(Tatget))

   ```java
   // 接口
   public interface Service(){
       void doSomething();
   }
   
   // 实现类
   public class ServiceImpl implements Service(){
       void doSomething(){
           system.out.println("Hello World");
       }
   }
   ```

2. 定义切面类

   ```java
   @Aspect // 是aspectj框架的注解，表示当前类是切面类
   public class MyAspectj() {
       @Before(value = "execution(public String plus.hf.s01.SomeServiceImpl.doSome(String, Integer))")
       public void fun1() {
           system.out.println("前置通知");
       }
       
   }
   ```

3. 注册目标类对象，切面类对象到容器中

   ```xml
       <bean id="someService" class="plus.hf.s01.SomeServiceImpl"/>
       <bean id="myAspect" class="plus.hf.s01.MyAspect"/>
   ```

4. 注册Aspectj自动代理

   通过扫描找到@Aspect定义的切面类，按通知类型和切入点，将其织入，生成代理

   ```xml
    <aop:aspectj-autoproxy/>
   ```

5. 测试

   ```java
   @Test
   public void aspect1(){
       ApplicationContext applicationContext = new ClassPathXmlApplicationContext("s01/SpringContext.xml");
       SomeService someService = (SomeService) applicationContext.getBean("someService");
       someService.doSome();
   }
   /*
   	输出：前置通知
   		 Hello World
   */
   ```

### 前置通知

在目标方法执行前执行, public void 方法名(){}

```java
@Aspect // 是aspectj框架的注解，表示当前类是切面类
public class MyAspectj() {
    @Before(value = "execution(public String plus.hf.s01.SomeServiceImpl.doSome(String, Integer))")
    public void fun1() {
        system.out.println("前置通知");
    }
    
}
```



### JoinPoint参数

每个通知类型都可以有这个参数，该参数本身是一个切入点表达式，通过该参数，可以获取切入点表达式，方法签名，目标对象等

```java
@Before(value = "execution(public void plus.hf.s01.SomeServiceImpl.fun(..))")
public void fun(JoinPoint joinPoint){
    Signature signature = joinPoint.getSignature(); // 获取目标方法签名（名称，参数等）
    Object[] args = joinPoint.getArgs(); // 获取目标方法的参数
    for (Object arg : args) {
        System.out.println(arg);
    }
    System.out.println("前置通知");
}
```

### 后置通知有returning属性

目标方法执行后，获取目标方法执行返回的结果。returning 用于指定接返回值的变量名。这里指定变量名为student，所以方法参数名称也要为student。方法参数类型一般用Object

所有后置方法可以有两个参数，一个是JoinPoint，一个是返回值

```java
@AfterReturning(value = "execution(public * plus.hf.s01.SomeServiceImpl.*(..))", returning = "student")
public void afterStudent(Object student){
    System.out.println("aspectj实现切面后置");
    if (student != null) {
        if (student instanceof Student) {
            ((Student) student).setName("hfc");
        }
    }
}
```

### 环绕通知有ProceedingJoinPoint参数

在目标方法执行前执行后执行。拦截目标方法，在环绕里面执行，当目标方法有返回值时，环绕通知就因该有返回值，所以环绕通知方法因该有返回值Object。ProceedingJoinPoint有proceed()方法用于执行目标方法，如果目标方法有返回值，那么该方法的返回值就是目标方法的返回值；当目标方法有参数时，ProceedingJoinPoint.getArgs()可以获取目标方法的参数。

```java
@Around(value = "execution(* plus.hf.s02.*.*(..))")
public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
    Object[] args = pjp.getArgs();
    Object ob = null;
    if (args != null && args.length >= 1) {
        System.out.println("环绕通知前切功能-----");
         ob = pjp.proceed(pjp.getArgs()); // 执行目标方法，并且获得目标方法的返回值
         if (ob instanceof Student) {
             ((Student) ob).setName("hfc");
         }
        System.out.println("环绕通知后切功能-----");
    }
    return ob; //返回目标方法的返回值
}
```

### 最终通知

不论目标方法是否报错都会执行

```java
@After(value = "execution(* plus.hf.s02.*.*(..))")
public void myAround(){
    System.out.println("不论目标方法是否报错都会执行");
}
```

### @Pointcut 定义切入点别名

当较多的通知增强方法使用相同的 execution 切入点表达式时，编写、维护均较为麻烦。AspectJ 提供了@Pointcut 注解，用于定义 execution 切入点表达式。

```java
@Before(value = "myPointCut()")
pubulic void before(){
    System.out.println("前置通知");
}

@AfterReturning(value = "myPointCut()", returning = "object")
pubulic void afterReturning(Object object){
    System.out.println("后置通知");
}

@Pointcut(value = "execution(* plus.hf.s02.*.*(..))")
private void myPointCut(){
    // 什么也不写
}
```

