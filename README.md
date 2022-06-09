# conference-submission-system

# 教程&文档
- spring boot + vue视频教程：https://www.bilibili.com/video/BV1NY411g7cf?p=1
- ant-design官方文档：https://www.antdv.com/components/input-cn  

# 项目配置
有两个文件夹，分别是ui、server，对应前端和后端。
数据库ip: 101.35.144.217，port: 3306，password: 123456.
后端采用springboot+mybatis-plus，用于提供一系列/api/xxx接口，IDEA可以运行
前端npm run dev运行，依赖大概有vite、pinia、等等（缺啥npm install啥吧）

# 大致设计思路
登录后localStorage会记录一个token，这个token的保质期一般是一个月，如果没有选记住我选项的话，那就是一天。

然后用户信息、权限都是动态的根据token获得，更加安全些。

一些需要权限的api，也是token+数据传送给后端，后端根据token验证数据有效性。

# 目前进度
## 晁哥
https://github.com/CCjiahao/conference-submission-system/commit/4ba7932d43ccb36ebf71abdfe1a8f924000f3c45  这个是昨天写的注册+发送验证码功能，可以参考这个，更加方便开发。
目前菜单的显示和跳转功能都实现了，登录和注册的ui我也写了一个特别丑的，功能差不多也实现了，一个不同就是手机号换邮箱了，因为手机号发验证码要钱。
但是登录和注册ui需要改改，表单验证我也没有做，勾选国家列表我写死在前端了，领域列表感觉需要后端获取，主席可以更改，这个也没有做。那我们下面就写自己对应的部分？尽量写完一个功能就push一下吧

现在投稿功能也写大致完了，大体功能完善，小细节后续再说。

## 铭哥


## 点哥
