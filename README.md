# ChatTogether-B/S结构社交平台
>  简单介绍
> >项目技术介绍：
>- Web应用后端主要使用Spring框架(其中用到的部分模块包括Spring MVC、Spring Security、Spring Data JPA、Spring WebSocket、Spring Messaging、Spring Test)
>- 数据库使用MySQL或嵌入式数据库h2database，在单元测试方面使用Spring Test、Junit 4、Mockito;
>- Web前端视图层使用Thymeleaf解释器，前端框架使用Materialize CSS、JQuery,WebSocket相关:stomp-websocket和sockjs-client;
> >系统环境
>- Java版本:JDK 1.8 +
>- 构建工具:Gradle 3.5 +
>- Tomcat:8.0 +
>- MySQL:5.5 +
> >功能实现
>1.	用户权限管理：多级用户权限设计与分配，一个用户可以拥有多级权限;
>2.	用户管理：用户账户密码重置、账号封禁、更新用户资料及头像上传和模糊搜索用户等功能;
>3.	微说管理：用户发表微说、模糊查询微说关键字、微说管理操作(删除)、微说点赞;
>4.	微说评论管理：微说评论二层级设计、评论内评论、评论点赞、评论管理操作;
>5.	好友管理：用户之前互加好友、好友管理、好友之间实时聊天推送(WebSocket实现)及通知推送的逻辑实现;
>6.	消息管理：系统推送消息、消息通知(微说点赞、微说回复、私信);
>7.	反馈建议管理：用户反馈、反馈查看和删除;
> 
>  展示
>
> ![](https://github.com/hdfg159/chattogether/blob/master/screenshots/1.png?raw=true)
> ![](https://github.com/hdfg159/chattogether/blob/master/screenshots/2.png?raw=true)
> ![](https://github.com/hdfg159/chattogether/blob/master/screenshots/3.png?raw=true)
> 