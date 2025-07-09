Object getBean(String name);// 需要手动转型

Class getBean(String name, String class); // 自动转型

Class getBean(String class); // 从容器中获取相同类型的bean，只能获取一个，如果同种类型的bean有两个则会报错
