# YEONDOO-MAIN_BE

<div align="center">
  <img width="80" alt="image" src="https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/22240e3d-8815-4aa4-9a34-6018ad09eb4d">
</div>

# 연두 - 웹 연구 어시스턴트
> **SW Maestro 14기 팀 Federation** <br/> **개발기간: 2023.06 ~ 2023.11**

## 배포 영상

## 개발팀 소개

|      이석우       |          임가윤         |       정찬호         |                                                                                                               
| :------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | 
|   <img width="160px" src="https://avatars.githubusercontent.com/u/83485983?v=4" />    |                      <img width="160px" src="https://avatars.githubusercontent.com/u/81891345?v=4" />    |                   <img width="160px" src="https://avatars.githubusercontent.com/u/62923434?s=64&v=4"/>   |
|   [@ProgrammingLee](https://github.com/IHateChem)   |    [@coddingyun](https://github.com/coddingyun)  | [@chjung99](https://github.com/chjung99)  |

## 프로젝트 소개

연두는 연구원들이 pdf를 통해 논문을 공부할때 도움을 주는 서비스입니다. 
연두 워크스페이스 생성이 가능하며, 각 워크 스페이스 별로
arXiv 논문검색, 논문 질의, pdf 노팅 및 하이라이팅이 가능합니다. 
## 시작 가이드
### Requirements
For building and running the application you need:

- [Node.js 14.19.3](https://nodejs.org/ca/blog/release/v14.19.3/)
- [Npm 9.2.0](https://www.npmjs.com/package/npm/v/9.2.0)
- [Strapi 3.6.6](https://www.npmjs.com/package/strapi/v/3.6.6)

### Installation
``` bash
$ git clone https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE
$ cd YEONDOO-MAIN_BE
```

### application.properties 작성
달러표시($)는 상황에 맞게 추가
``` bash
$ vi src/main/resources/application.properties
```

#main
spring.profiles.active=local
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb:${db_url}
spring.datasource.username=${db_name}
spring.datasource.password=${db_pw}

spring.google.client_id=${spring.google.client_id}
spring.google.client_secret=${spring.google.client_secret}
#redis:
spring.data.redis.host: ${redis_host}
spring.data.redis.port: 6379
#jwt
jwt.secret=${jwt_secret}
#MyBatis
mybatis.type-aliases-package=com.example.yeondodemo.dto
mybatis.configuration.map-underscore-to-camel-case=true
logging.level.com.example.yeondodemo.repository=trace

#spring.config.activate.on-profile:local
#spring.config.activate.on-profile:production

#Python
python.address = ${ai_server_address}
python.key = ${ai_server_address}


serpapi.key = 

#pdf ??
spring.servlet.multipart.maxFileSize=10MB
spring.servlet.multipart.maxRequestSize=10MB

#S3
aws.accessKey=${s3_access_key}
aws.secretKey=${s3_sceret_key}


#### Backend
```
$ cd strapi-backend
$ nvm use v.14.19.3
$ npm install
$ npm run develop
```

#### Frontend
https://github.com/YEONDOO-swm/YEONDOO-fe

#### AI
https://github.com/YEONDOO-swm/yeondoo-fastapi
---

