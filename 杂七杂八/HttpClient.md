## 作用

模拟浏览器发送http请求，发送请求参数，并接收响应

RPC远程接口调用

## 使用

依赖

```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.13</version>
</dependency>
```

发送get请求

```java
public void httpGet() {
    // 创建客户端， 发送请求， 接受返回结果
    CloseableHttpClient client = HttpClients.createDefault();
    String url = "https://restapi.amap.com/v3/ip?key=0113a13c88697dcea6a445584d535837&ip=60.25.188.64";

    // 创建HttpGet对象
    HttpGet httpGet = new HttpGet(url);

    // 执行请求
    try {
        // 执行get请求并接收返回的结果
        CloseableHttpResponse response = client.execute(httpGet);
        // 如果返回状态值为200
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String string = EntityUtils.toString(response.getEntity());
            System.out.println("应答结果" + string);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

发送post请求

```java
public void testPost() {
    // 客户端
    CloseableHttpClient client = HttpClients.createDefault();

    String url = "https://restapi.amap.com/v3/ip";
    
    // post请求
    HttpPost httpPost = new HttpPost(url);

    try {
        // 请求参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", "0113a13c88697dcea6a445584d535837"));
        params.add(new BasicNameValuePair("ip", "60.25.188.64"));
        HttpEntity entity = new UrlEncodedFormEntity(params);
        httpPost.setEntity(entity);
        //客户端发送请求并接收返回结果
        CloseableHttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String str = EntityUtils.toString(response.getEntity());
            System.out.println("str=" + str);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

