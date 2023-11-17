# YEONDOO-MAIN_BE

<div align="center">
  <img width="80" alt="image" src="https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/22240e3d-8815-4aa4-9a34-6018ad09eb4d">
</div>

# 연두 - 웹 연구 어시스턴트
> **SW Maestro 14기 팀 Federation** <br/> **개발기간: 2023.06 ~ 2023.11**

## 배포 주소
 https://yeondoo.net

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

- [Springboot 3.1.1]
- [JDK 17]

### Installation
``` bash
$ git clone https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE
$ cd YEONDOO-MAIN_BE
```

### application.properties 작성
달러표시($)는 상황에 맞게 추가
``` bash
$ vi src/main/resources/application.properties
```
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
```

#### Backend Build & Run
```
$ ./gradlew build
$ java -jar build/libs/yeondoDemo-0.0.1-SNAPSHOT.jar
```

#### Frontend
https://github.com/YEONDOO-swm/YEONDOO-fe

#### AI
https://github.com/YEONDOO-swm/yeondoo-fastapi

---

## Stacks

### Environment
![IntelliJ](https://img.shields.io/badge/Intellij%20Idea-000?logo=intellij-idea&style=for-the-badge)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)                   

### Development
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

### OS
![Ubuntu](https://img.shields.io/badge/-Linux-grey?logo=linux)

### Infra
![AWS EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)


### DB
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![mariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

### Communication
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

---

## DB 구조
![image](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/c5536be2-e344-4158-9a81-9105e49fd59e)
## API 
![image](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/fab0fa42-06c2-404a-a97c-fe80622bddcb)
## 인프라 구조
<img width="637" alt="스크린샷 2023-11-15 오전 10 18 38" src="https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/807b1144-c402-47a7-86d8-32df329cb0d7">

---
## 화면 구성 
| 랜딩 페이지  |
| :------------: |
| ![EUx910gA1nRroa5cu9d-TtMx88qcnKbD8J_BUdv-XTLyV1DwAm_t6e1ySUgvwHBELmilYi0Lql9kkfcsU4cNso5Pf_uXVjk_FBzTKqqS7y53UMYc0Jnz5k1c_8Kl](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/1db24821-fd66-463b-bedd-6949bb68827f)|
| 워크스페이스  |
| :------------: |
![x4KwnD7oH0JdegRc2aU4korfz50Ly3mGsU5x3kdMbggSPldnsBdh8GmRHcx6zgQuEa87Q9UvLQXj0m_-OqareTqG3ScWmDDCP4XvyOLb_ubmbmAlCCJc4Qq7Y8bT](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/6ecab8c2-123c-4f18-9230-c732825364fc) |  
| 대쉬보드   |
| :------------: |
| ![J_OR9bfHZvsByY9Xn7eAs6rB5n8jb2kXk_WKLGOOaClbCL61bezDYafwkNx6QJG0lajSemalrvaWQ38QYrTE-_5luQfGSAH6PjfZ3nAKnZ7WkVAgi03Nkg4UrU0A](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/7fc4ec16-f44f-4f78-85f0-2c2803153409)|
| StudyWithAI   |  
| :------------: |
![gZq7px8BVd1GDaE80cfLyaWTg8ZmXZzrSMJ652Kf7cww5XeRLv12miX4sJm9sDwIy37IDEG_qAC0yp9ol0UzBAoxitp0j8FBAUpUEk74yrX8D422aUp57wKf5wWp](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/bd8b6e72-6e4a-4376-bee3-b964465498ec) |

---
## 주요 기능 📦

### 워크스페이스 생성
- 분야별 워크스페이스 생성 가능
<img width="1110" alt="스크린샷 2023-11-15 오전 10 31 49" src="https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/138e89b8-3a2b-4a5b-8476-53797f62873a">

### 검색기능
- google API를 통한 arXiv 논문검색
![C4DBgCNSFr-atZdNXe--6pQibW0cEw9-TKjRJoagFxiiG-cRKycDyybOcv9w2IkHKpL3VxqTd6fPq76c0tCr_DujSObyFDDGfXEoDxFV7j4oAySPEAfto_SfxnF9](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/7d18c76c-6011-4d04-96c8-56b9f097ab6e)

### 논문 보관하기
- My Works에 추가 할 수 있다.

### 논문 추가하기
- arXiv가 아닌 논문은 직접 추가해서 서비스를 이용할 수 있다.

### Study With AI
- 연두 챗봇에게 해당 논문에 대한 질의 가능
- 한가지 논문뿐 아니라 여라가지 논문에 대해서도 질문 가능
- 노팅 하이라이팅등, pdf꾸미기 기능 제공
- 작업물 pdf로 다운로드 가능
<img width="395" alt="jns0DKtBonn-DZbdxua2zmWN4ypWmi0EVZ9Pec_7uUaaDB6Y3rS1hadH_Gn5rMd7odCPmSr5-ZNbdaG1lXrF4s90Gz5eZeNFg0kpF4t-r9sc96k0WR8N63zXwEJ0" src="https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/d832d703-009a-4876-b20d-c2d665422f1e">

### History
- 챗봇에게 질의응답한 내역 확인가능
- 관심해제 논문 복구 가능

### My Library
- 모든 워크스페이스의 논문들을 모아서 확인 가능
- ![rhc1VKaTbIB_xESvTVqBYdwrFiVAPJ15gMJzmaNcwnK9EWnMTCTkS4Z5oE0KNgPt-wwn84MfW4zgccQIj0dKw_BTp8qszfLTcVKKSSg64T2fJAqrkipwH3JTzgxp](https://github.com/YEONDOO-swm/YEONDOO-MAIN_BE/assets/83485983/15e54d01-7056-49bc-acfd-fce3f8c9a56b)

---
## 아키텍쳐

-->

