# 🥗 샐러데이 앱

## 📌 프로젝트 소개

샐러데이는 샐러드 전문 매장의 주문, 결제, 포인트, 할인 정책을 통합 관리하기 위한 키오스크 및 관리자 플랫폼입니다.

사용자는 키오스크 앱을 통해 메뉴를 조회하고 주문 및 결제를 진행할 수 있으며, 

관리자는 별도의 Admin 시스템에서 주문 현황, 재고 상태, 포인트 및 할인 정책을 관리할 수 있습니다.

샐러데이 앱은 **Spring Boot + JPA 기반의 REST API 서버**,  
**React 기반의 사용자 뷰 앱**,  
**Spring Boot + MyBatis + Thymeleaf 기반의 관리자(Admin) 웹**으로 구성된 통합 플랫폼입니다.


## 🎯 개발 목표

- 실제 매장에서 사용할 수 있는 주문 프로세스 구현
- 결제 및 포인트 적립/사용 기능 제공
- 재고 및 품절 상태 관리
- 할인 정책의 유연한 운영 지원
- 사용자 서비스와 관리자 서비스를 분리한 구조 설계


## 🏗 시스템 구성

```
React (사용자 키오스크)
        │
        ▼
Spring Boot REST API
(JPA + Spring Security)
        │
        ▼
MariaDB
        │
        ▼
Toss Payments

관리자(Admin)
Spring Boot + MyBatis + Thymeleaf
```


## 🛠 기술 스택

| 영역        | 기술 구성                                                                 
|-------------|---------------------------------------------------------------------------
| 🔙 Backend  | Spring Boot, Spring Security, JPA (Hibernate), MariaDB, Lombok           
| 🖥 Frontend | React, Axios, TailwindCSS                                                 
| 🛠 Admin    | Spring Boot, MyBatis, Thymeleaf, Bootstrap (Mazer Template)              
| ⚙ Infra    | Gradle/Maven                                    

## 📦 프로젝트 구조

```
saladay_api
├─ controller
├─ domain
│   ├─ common
│   ├─ discount
│   ├─ menu
│   ├─ orders
│   ├─ point
│   └─ users
├─ dto
│   ├─ discountDTO
│   ├─ menuDTO
│   ├─ ordersDTO
│   ├─ pointDTO
│   ├─ priceDTO
│   ├─ tossDTO
│   └─ usersDTO
├─ repository
├─ service
├─ sms
└─ util
```
## 📂 핵심 도메인
```
Users
 ├─ Orders
 │    ├─ OrdersItem
 │    └─ AppliedDiscount
 │
 └─ Point

Category
 └─ Menu
      ├─ MenuOption
      └─ MenuInventory

Discount
```


## ✨ 주요 기능
🛒 주문
- 메뉴 및 옵션 선택
- 옵션별 추가 금액 계산
- 주문 금액 자동 산출
  
💳 결제
- Toss 결제 연동
- 결제 승인 및 실패 처리
- 주문 상태 관리
  
🎁 포인트
- 휴대폰 번호 기반 회원 조회
- 포인트 적립
- 포인트 사용
- 결제 취소 시 포인트 복원
  
🎯 할인 정책
- 정액 할인
- 정률 할인
- 기간 한정 할인
- 우선순위 기반 할인 적용
  
📦 재고 관리
- 재고 수량 관리
- 수동 품절 처리
- 부분 품절 처리
- 주문 시 재고 재검증
  
📊 관리자 페이지
- 주문 현황 조회
- 상품 관리
- 할인 정책 관리
- 포인트 관리
- 재고 관리
  

## 🔍 주요 설계 내용

### 1. 트리형 DTO 응답 구조

키오스크 화면 구조에 맞추어

```
Category
 └─ Menu
      ├─ MenuOption
      └─ MenuInventory
```

형태의 트리 구조 DTO를 설계하였습니다.

이를 통해 프론트엔드에서 추가 가공 없이 화면에 바로 렌더링할 수 있도록 구현하였습니다.

### 2. 판매 가능 상품만 노출

메뉴 조회 시 재고 상태와 품절 여부를 함께 계산하여 판매 가능한 메뉴만 사용자에게 노출하도록 구현하였습니다.

```java
.filter(MenuDTO::isAvailable)
```

### 3. 할인 우선순위 정책

여러 할인 정책이 동시에 적용되는 상황에서 우선순위(Priority)를 기준으로 정렬하여 일관된 할인 결과를 제공하도록 구현하였습니다.

### 4. 기존 설계 영향 최소화

프로젝트 진행 중 옵션 수량 기능이 추가되었으나, 기존 데이터베이스 구조를 전면 수정하기보다 DTO 및 매핑 계층을 활용하여 기능을 확장하였습니다.

이를 통해 기존 기능에 대한 영향도를 최소화하면서 요구사항을 반영하였습니다.


## 🚀 프로젝트를 통해 얻은 경험

- Spring Boot REST API 설계
- JPA 기반 도메인 모델링
- MyBatis와 JPA 혼합 운영
- Toss 결제 API 연동
- 포인트 및 할인 정책 구현
- 재고 및 품절 관리 로직 구현
- 비즈니스 요구사항에 따른 데이터 모델 확장 경험

## 🖼 보기 화면
![초기 화면](https://github.com/user-attachments/assets/b2bd1201-edef-463b-afb7-ccb200256ce7)
![상품 확인](https://github.com/user-attachments/assets/4f8aaef3-92f7-4faa-8560-933ed79396e5)
![결제](https://github.com/user-attachments/assets/2ccd1496-9989-4277-a12f-b5be01c26853)

---

🔗 어드민 앱과 사용자 뷰 앱은 아래 링크에서 다운로드할 수 있습니다:

- 👤 사용자 뷰 앱: [샐러데이 키오스크](https://github.com/Nodabp/salady_kiosk)  
- 🛠 관리자(Admin) 앱: [샐러데이 어드민](https://github.com/Nodabp/saladay_admin)


