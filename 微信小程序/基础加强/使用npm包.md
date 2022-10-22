# Vant Weapp

## 安装 Vant 组件库

https://youzan.github.io/vant-weapp/#/quickstart#an-zhuang

1.初始化npm

* 右键 -> 在外部终端窗口打开 -> npm init -y

2.安装

```cmd
npm i @vant/weapp -S --production
```

3.修改app.json

>将 app.json 中的 `"style": "v2"` 去除，小程序的[新版基础组件](https://developers.weixin.qq.com/miniprogram/dev/reference/configuration/app.html#style)强行加上了许多样式，难以覆盖，不关闭将造成部分组件样式混乱。

4.构建npm包

![img](%E4%BD%BF%E7%94%A8npm%E5%8C%85.assets/fa0549210055976cb63798503611ce3d.png)

5.使用Vant

* 在需要引用的页面的.json文件中添加需要使用的组件

  ```json
  "usingComponents": {
      "van-button": "@vant/weapp/button/index"
  }
  ```

* 在页面使用对于的组件

  ```html
  <van-button type="default">默认按钮</van-button>
  <van-button type="primary">主要按钮</van-button>
  <van-button type="info">信息按钮</van-button>
  <van-button type="warning">警告按钮</van-button>
  <van-button type="danger">危险按钮</van-button>
  ```

  