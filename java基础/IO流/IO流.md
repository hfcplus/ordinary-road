# FileInputStream和FileOutputStream

## FileInputStream

```java
// 通过构造方法指定要读取哪个文件,如果要读取的文件不存在则运行后会产生异常
FileInputStream fis = new FileInputStream("e:/file/abc.txt");

// 通过file 创建FileInputStream
File file = new File("e:/file/abc.txt");
FileInputStream fileInputStream = new FileInputStream(file);
```

|        方法        |                             解释                             |
| :----------------: | :----------------------------------------------------------: |
|  int available()   |                        剩余可读字节数                        |
|     int read()     |              一次读取一个字节，返回该字节的码值              |
| int read(byte[] b) | 文件中读取若干字节保存到bytes数组中,返回读到的字节数量,-1表示读完 |

## FileOutputStream

构造方法

```java
// 如果文件不存在则会创建一个新文件,true代表追加，没有true就会删掉该文件的所有内容，重新写
FileOutputStream(File file);
FileOutputStream(File file, boolean append) 
FileOutputStream(String name) 
FileOutputStream(String name, boolean append) 
```

|                  方法                  |                  解释                   |
| :------------------------------------: | :-------------------------------------: |
|           void write(int b)            |        一次写入一个码值为b的字节        |
|          void write(byte[] b)          |         一次性写入整个byte数组          |
| void write(byte[] b, int off, int len) | 将b字节数组从off下标开始，len个字节写入 |
|                                        |                                         |

复制某个文件文件

```java
FileInputStream fis = new FileInputStream("e:/file/abc.txt");
FileOutputStream fos = new FileOutputStream("e:/file/abcd.txt", true); // true,代表追加
byte[] bytes = new byte[1024 * 8]; // 1024 * 8,比较慢.1024 * 8 * 4速度还行
int len = file.read(bytes); // 返回读取的字节个数
while (len != -1) {
    fos.write(b, 0, len); // 录入新读取的字节，倒数第二次读取的字节课呢并没有b.length那么多，所以要采取这个方法
    len = file.read(bytes);
}
```

