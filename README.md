# easy-create

项目脚手架-简易创建

项目演示地址 [Quick Start](http://wly.mail.wo.cn/)

```
version: 
    1.0.0-SNAPSHOT
        1.仅支持创建Maven项目
        2.仅支持语言为Java
        3.支持SpringBoot1.x或2.x项目创建
        4.支持Assembly或SpringBoot打包方式
        5.支持单体或复合项目结构
```

- **开发环境脚本**
`nohup java -jar easy-create-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev >/dev/null 2>&1 &`

- **生产环境脚本**
`nohup java -jar easy-create-1.0.0-SNAPSHOT.jar --spring.profiles.active=pro >/dev/null 2>&1 &`