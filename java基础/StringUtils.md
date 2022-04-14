工具类`org.apache.commons.lang3.StringUtils`是JDK提供的操作String类型数据常用工具类，在此整理了备用。



## 1.public static boolean contains(String str,char searchChar)

说明：
str是原始字符串，searchChar是想要搜索的字符，此方法是检查字符串str中是否包含字符searchChar，如果str为null或””，则返回false。

示例：

```java
StringUtils.contains(null, *)    = false
StringUtils.contains("", *)      = false
StringUtils.contains("abc", 'a') = true
StringUtils.contains("abc", 'z') = false
```

## 2.public static boolean contains(String str,String searchStr)

说明：
str是原始字符串，searchStr是待搜索的字符串，此方法是检查字符串str中是否包含字符串searchStr，如果str为null，则返回false。

示例：

```java
StringUtils.contains(null, *)     = false
StringUtils.contains(*, null)     = false
StringUtils.contains("", "")      = true
StringUtils.contains("abc", "")   = true
StringUtils.contains("abc", "a")  = true
StringUtils.contains("abc", "z")  = false
String eq = "合上#2炉B送风机电机ⅡB-14开关控制小开关。";
List<String> sList = new ArrayList();
sList.add("#2炉B送风机电机ⅡB-14开关");
sList.add("#2");
for(String s:sList){
    if(StringUtils.contains(eq,s)){
         System.out.println("包含："+s);
    }
    /*if(eq.indexOf(s)!=-1){
     System.out.println("包含："+s);
    }*/
}
```

打印结果如下：

```
包含：#2炉B送风机电机ⅡB-14开关
包含：#2
```

## 3.public static boolean containsIgnoreCase(String str,String searchStr)

说明：
在字符串str中搜索字符串searchStr，忽略大小写。如果str为Null,则返回false。

示例：

```java
StringUtils.contains(null, *) = false
StringUtils.contains(*, null) = false
StringUtils.contains("", "") = true
StringUtils.contains("abc", "") = true
StringUtils.contains("abc", "a") = true
StringUtils.contains("abc", "z") = false
StringUtils.contains("abc", "A") = true
StringUtils.contains("abc", "Z") = false
```

## 4.public static boolean isBlank(String str)

说明：
检查一个字符串str是否是空白(whitespace)，empty (“”)或者null；

示例：

```java
StringUtils.isBlank(null)      = true
StringUtils.isBlank("")        = true
StringUtils.isBlank(" ")       = true
StringUtils.isBlank("bob")     = false
StringUtils.isBlank("  bob  ") = falseja
```

## 5.public static boolean isEmpty(String str)

说明：
检查一个字符串str是否是empty (“”) 或 null。

示例：

```java
StringUtils.isEmpty(null)      = true
StringUtils.isEmpty("")        = true
StringUtils.isEmpty(" ")       = false
StringUtils.isEmpty("bob")     = false
StringUtils.isEmpty("  bob  ") = false
```

## 6.public static boolean isNotBlank(String str)

说明：
判断一个字符串str非empty (“”), 非 null ，非 whitespace。

示例：

```java
StringUtils.isNotBlank(null)      = false
StringUtils.isNotBlank("")        = false
StringUtils.isNotBlank(" ")       = false
StringUtils.isNotBlank("bob")     = true
StringUtils.isNotBlank("  bob  ") = true
```

## 7.public static String trimToNull(String str)

说明：
移除字符串中字符的char<32（ASCII<32）的字符，如果该字符串是null或empty(“”)，则返回null。

示例：

```java
StringUtils.trimToNull(null)          = null
StringUtils.trimToNull("")            = null
StringUtils.trimToNull("     ")       = null
StringUtils.trimToNull("abc")         = "abc"
StringUtils.trimToNull("    abc    ") = "abc"
StringUtils.trimToNull("    a b c    ") = "a b c"
```

## 8.public static String trimToEmpty(String str)

说明：
移除字符串中字符的char<32（ASCII<32）的字符，如果该字符串是null或empty(“”)，则返回empty(“”)。

示例：

```java
StringUtils.trimToEmpty(null)          = ""
StringUtils.trimToEmpty("")            = ""
StringUtils.trimToEmpty("     ")       = ""
StringUtils.trimToEmpty("abc")         = "abc"
StringUtils.trimToEmpty("    abc    ") = "abc"
```

## 9.public static String replace(String text,String searchString,String replacement)

说明：
用一个字符串替换另一个字符串中出现的指定字符串。

示例：

```java
StringUtils.replace(null, *, *)        = null
 StringUtils.replace("", *, *)          = ""
 StringUtils.replace("any", null, *)    = "any"
 StringUtils.replace("any", *, null)    = "any"
 StringUtils.replace("any", "", *)      = "any"
 StringUtils.replace("aba", "a", null)  = "aba"
 StringUtils.replace("aba", "a", "")    = "b"
 StringUtils.replace("aba", "a", "z")   = "zbz"
```

参数：

- text:需要被替换的字符串所在的字符串，可能为null；
- searchString:需要被替换的字符串，可能为null；
- replacement：用来替换的字符串，可能为null；

## 10.public static String join(Object[] array,String separator);

说明：

用指定分隔符连接数组各个元素。

示例：

```java
StringUtils.join(null, *)                = null
StringUtils.join([], *)                  = ""
StringUtils.join([null], *)              = ""
StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
StringUtils.join(["a", "b", "c"], null)  = "abc"
StringUtils.join(["a", "b", "c"], "")    = "abc"
StringUtils.join([null, "", "a"], ',')   = ",,a"
```

这里以数组为例，集合等类型的详细方法参考文章最下面提供的官方文档。

## 11.public static boolean endsWith(String str, String suffix)

示例：

```java
StringUtils.endsWith(null, null)      = true
StringUtils.endsWith(null, "def")     = false
StringUtils.endsWith("abcdef", null)  = false
StringUtils.endsWith("abcdef", "def") = true
StringUtils.endsWith("ABCDEF", "def") = false
StringUtils.endsWith("ABCDEF", "cde") = false
```

## 12.public static boolean equals(String str1,String str2)

说明：

比较两个字符串，如果相同返回true，如果两个字符串都为null，返回true。此方法与String类的equals方法相比，可以判断两个null值。

示例：

```java
 StringUtils.equals(null, null)   = true
 StringUtils.equals(null, "abc")  = false
 StringUtils.equals("abc", null)  = false
 StringUtils.equals("abc", "abc") = true
 StringUtils.equals("abc", "ABC") = false
```

## 13. StringUtils.substringAfter();

说明：

截取某字符串指定字符串之后的内容。

示例：

```java
String filePath = "/common-upload-ftp/370000/20200611/d9b3cd34e2fe419783b2a0085c71b422/1591843119657.pdf";
String fileRealPath = StringUtils.substringAfter(filePath, "/common-upload-ftp");
```