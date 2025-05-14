# SmartWardrobe

간단한 의류 관리 서비스 프로젝트입니다.

현재는 회원가입·로그인, JWT 기반 인증·인가, 관리자 권한 관리 API와 Swagger 문서화를 구현했습니다.

## 주요 기능
- **회원가입**
    - 일반 사용자(ROLE_USER) 및 관리자(ROLE_ADMIN) 가입
- **로그인**
    - JWT 발급
- **관리자 권한 부여**
    - Admin API로 다른 사용자에게 ADMIN 권한 부여
- **인증·인가**
    - 모든 보호된 API는 `Authorization: Bearer <JWT>` 헤더 필요
    - 토큰 검증, 만료, 권한 검사
- **Swagger UI**
    - `/swagger-ui.html` 에서 바로 API 문서 확인 및 테스트

---
## 기술 스택

### Backend
<!-- Java -->
<a href="https://www.oracle.com/java/">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white" alt="Java"/>
</a>
<!-- Spring Boot -->
<a href="https://spring.io/projects/spring-boot">
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white" alt="Spring Boot"/>
</a>
<!-- Gradle -->
<a href="https://gradle.org/">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white" alt="Gradle"/>
</a>

### DB
<!-- H2 Database -->
<a href="https://www.h2database.com/">
  <img src="https://img.shields.io/badge/H2-007396?style=flat-square&logo=h2&logoColor=white" alt="H2 Database"/>
</a>

### Security
<!-- Spring Security -->
<a href="https://spring.io/projects/spring-security">
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=spring-security&logoColor=white" alt="Spring Security"/>
</a>
<!-- JWT (JJWT) -->
<a href="https://github.com/jwtk/jjwt">
  <img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=json-web-tokens&logoColor=white" alt="JJWT"/>
</a>

### API 문서화
<!-- Swagger UI -->
<a href="https://swagger.io/tools/swagger-ui/">
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white" alt="Swagger UI"/>
</a>

---

## API 명세

### 1. Auth API

| 기능               | Method | URL                  | Request Header                         | Request Body      | Response Body      | Response Status                                    |
|------------------|:------:|---------------------|----------------------------------------|------------------|--------------------|-----------------------------------------------------|
| 회원 가입          | POST   | `/signup`           | -                                      | `SignUpRequest`  | `SignUpResponse`   | `201 Created`<br>`400 Bad Request`                  |
| 관리자 회원 가입     | POST   | `/adminsignup`      | `Authorization: Bearer <token>`        | `SignUpRequest`  | `SignUpResponse`   | `201 Created`<br>`400 Bad Request`<br>`401 Unauthorized`<br>`403 Forbidden` |
| 로그인             | POST   | `/login`            | -                                      | `LoginRequest`   | `TokenResponse`    | `200 OK`<br>`400 Bad Request`                       |

### 2. Admin API

| 기능                                        | Method | URL                           | Request Header                  | Request Body        | Response Body        | Response Status                                                                                    |
| ----------------------------------------- | :----: | ----------------------------- | ------------------------------- | ------------------- | -------------------- | -------------------------------------------------------------------------------------------------- |
| 권한 변경|  PATCH | `/admin/users/{userId}/roles` | `Authorization: Bearer <token>` | `RoleChangeRequest` | `RoleChangeResponse` | `200 OK`<br>`400 Bad Request` (잘못된 입력)<br>`401 Unauthorized`<br>`403 Forbidden`<br>`404 Not Found` |


## Swagger
접속 방법 : http://localhost:8080/swagger-ui/index.html#/

![img.png](picture/img.png)

### Auth
#### signup
![img_1.png](picture/img_1.png)
![img_2.png](picture/img_2.png)

#### signin
![img_3.png](picture/img_3.png)
![img_4.png](picture/img_4.png)

### User
![img_5.png](picture/img_5.png)
![img_6.png](picture/img_6.png)