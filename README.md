# 🥗 샐러데이 앱

## 📌 프로젝트 개요  
샐러데이 앱은 **Spring Boot + JPA 기반의 REST API 서버**,  
**React 기반의 사용자 뷰 앱**,  
**Spring Boot + MyBatis + Thymeleaf 기반의 관리자(Admin) 웹**으로 구성된 통합 플랫폼입니다.  

> 주문 관리, 결제, 포인트 및 할인 정책 운영을 지원하는 서비스입니다.

---

## 🛠 기술 스택

| 영역        | 기술 구성                                                                 
|-------------|---------------------------------------------------------------------------
| 🔙 Backend  | Spring Boot, Spring Security, JPA (Hibernate), MariaDB, Lombok           
| 🖥 Frontend | React, Axios, TailwindCSS                                                 
| 🛠 Admin    | Spring Boot, MyBatis, Thymeleaf, Bootstrap (Mazer Template)              
| ⚙ Infra    | Gradle/Maven, GitHub Actions (CI/CD)                                      


    A[React 사용자 앱] --> B[Spring Boot + JPA REST API 서버]
    B --> C[MariaDB]
    B --> D[Toss 결제 API]
    B --> E[Spring Boot + MyBatis + Thymeleaf Admin]

## ✨ 주요 기능

### 👤 사용자 앱
- 🛒 주문 기능  
- ❌ 품절 상품 처리 확인  
- 💳 Toss 결제 연동  
- 📱 휴대폰 번호 기반 포인트 사용 / 적립  
- 🎯 할인 적용 및 우선순위 정책 반영  

### 🛠 관리자(Admin)
- 📊 주문 현황 관리  
- ❌ 상품 품절 처리  
- 🎁 포인트 및 할인 정책 관리  
- 🔁 할인 재사용 / 기간 설정  
- ⬆️ 할인 우선순위 설정  

---

## 🖼 보기 화면
![초기 화면](https://github.com/user-attachments/assets/b2bd1201-edef-463b-afb7-ccb200256ce7)
![상품 확인](https://github.com/user-attachments/assets/4f8aaef3-92f7-4faa-8560-933ed79396e5)
![결제](https://github.com/user-attachments/assets/2ccd1496-9989-4277-a12f-b5be01c26853)

---

🔗 어드민 앱과 사용자 뷰 앱은 아래 링크에서 다운로드할 수 있습니다:

- 👤 사용자 뷰 앱: [샐러데이 키오스크](https://github.com/Nodabp/salady_kiosk)  
- 🛠 관리자(Admin) 앱: [샐러데이 어드민](https://github.com/Nodabp/saladay_admin)


