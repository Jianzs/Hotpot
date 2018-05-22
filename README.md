# Hotpot
Wechat Mini Program Contest Project

团队协作，相互督促学习  
亦可个人记录，提醒自己

```
.
├── API.md
├── mvnw*
├── mvnw.cmd
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── cn/
    │   │       └── yzh/
    │   │           └── hotpot/
    │   │               ├── configures/
    │   │               │   ├── CorsConfig.java
    │   │               │   └── InterceptorConfig.java
    │   │               ├── dao/
    │   │               │   ├── projection/
    │   │               │   │   ├── HistoryTaskListProjection.java
    │   │               │   │   ├── PendingTaskListProjection.java
    │   │               │   │   └── PersonScoreProjection.java
    │   │               │   ├── ScoreDao.java
    │   │               │   ├── TaskDao.java
    │   │               │   ├── TaskGroupDao.java
    │   │               │   ├── TaskItemDao.java
    │   │               │   ├── TaskItemDayDao.java
    │   │               │   ├── TaskMemberDao.java
    │   │               │   ├── TaskMemberDayDao.java
    │   │               │   └── UserDao.java
    │   │               ├── enums/
    │   │               │   ├── ResponseStatusEnum.java
    │   │               │   ├── TaskFinishStatusEnum.java
    │   │               │   ├── TaskGroupTypeEnum.java
    │   │               │   ├── UserGenderEnum.java
    │   │               │   ├── UserInfoEnum.java
    │   │               │   └── UserRoleEnum.java
    │   │               ├── exception/
    │   │               │   ├── ConnectWechatException.java
    │   │               │   ├── handler/
    │   │               │   │   ├── DefaultExceptionHandler.java
    │   │               │   │   └── WechatExceptionHandler.java
    │   │               │   └── NoAuthorizationException.java
    │   │               ├── HotpotApplication.java
    │   │               ├── pojo/
    │   │               │   ├── dto/
    │   │               │   │   ├── OptionDto.java
    │   │               │   │   └── ResponseDto.java
    │   │               │   └── entity/
    │   │               │       ├── ScoreEntity.java
    │   │               │       ├── TaskGroupEntity.java
    │   │               │       ├── TaskItemDayEntity.java
    │   │               │       ├── TaskItemEntity.java
    │   │               │       ├── TaskMemberDayEntity.java
    │   │               │       ├── TaskMemberEntity.java
    │   │               │       └── UserEntity.java
    │   │               ├── service/
    │   │               │   ├── impl/
    │   │               │   │   ├── TaskServiceImpl.java
    │   │               │   │   └── UserServiceImpl.java
    │   │               │   ├── TaskService.java
    │   │               │   └── UserService.java
    │   │               ├── util/
    │   │               │   ├── DatetimeUtil.java
    │   │               │   ├── JWTUtil.java
    │   │               │   └── WechatUtil.java
    │   │               └── web/
    │   │                   ├── controller/
    │   │                   │   ├── TaskController.java
    │   │                   │   └── UserController.java
    │   │                   └── interceptor/
    │   │                       ├── AuthorizationInterceptor.java
    │   │                       └── ParseJWTInterceptor.java
    │   └── resources/
    │       ├── application-dev.properties
    │       ├── application.properties
    │       ├── application-pro.properties
    │       ├── static/
    │       └── templates/
    └── test/
        └── java/
            └── cn/
                └── yzh/
                    └── hotpot/
                        └── HotpotApplicationTests.java

29 directories, 53 files
```