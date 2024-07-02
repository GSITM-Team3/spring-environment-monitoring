# Jeju for Suffer
## Spring Boot를 활용한 제주 해수욕장 커뮤니티 서비스

## 🏄 소개
**배경**   
많은 사람들이 방문하는 인기있는 여행지 중 제주도를 선택합니다.   
특히 제주의 해수욕장은 매력적인 자연 풍경과 함께 시원한 바다를 즐길 수 있는 장소로 손꼽힙니다.    
그러나 해양 환경의 변화에 따라 해수욕장의 수질 정보와 날시는 사용자 경험에 큰 영향을 미칩니다.   


<br>

**목표**    
이 프로젝트는 제주도의 해수욕장 방문을 계획하는 사용자들이 더 나은 환경에서 안전하게 바다를 즐길 수 있도록 돕기 위해 기획되었습니다.   
제주도의 자연환경을 즐기는 사용자들에게 필요한 실용적인 정보와 소통의 장을 제공합니다.   
또한, 해수욕장에서 바로 서핑 클래스를 예약할 수 있는 사이트를 구현하여 실시간 예약 상태를 확인할 수 있도록 사용자 경험을 최적화하고    
현장에서의 스트레스를 최소화하며 안전한 여행 경험을 제공하고자 합니다.    

<br>

    
## 💻 개발환경
개발 도구 <img src="https://img.shields.io/badge/SpringBoot-088A08"> <img src="https://img.shields.io/badge/HTML-FF0000"> <img src="https://img.shields.io/badge/CSS-013ADF"> <img src="https://img.shields.io/badge/JavaScript-D7DF01">

라이브러리 <img src="https://img.shields.io/badge/Lombok-AC58FA"> <img src="https://img.shields.io/badge/thymeleaf-FF00FF"> <img src="https://img.shields.io/badge/security-01A9DB"> <img src="https://img.shields.io/badge/jackson-688A08"> <img src="https://img.shields.io/badge/validation-61210B"> <img src="https://img.shields.io/badge/dataformat-DF0101">

ORM  <img src="https://img.shields.io/badge/JPA-FA8258"> 

DB  <img src="https://img.shields.io/badge/H2-013ADF"> 
<br>

## 🙋‍♂️🙋‍♀️ 참고사항
1. 서핑클래스 db 초기 설정을 위하여 respository에 첨부되어 있는 "db 초기 insert.txt"파일의 쿼리문을 h2-console에 입력해주세요.
   
<br>

## 🛠 기능 요약
### 1. 해수욕장별 수질 지표 & 위치 조회
- 해수욕장별 수질 데이터를 차트로 시각화 서비스 제공
- 해수욕장에 따른 위치 정보 제공
### 2. 실시간 날씨 정보
- 기상청 실시간 API를 활용한 날씨 정보 서비스 제공
- 구역별 세부 정보 제공
### 3. 커뮤니티
- 이미지 첨부가 가능한 게시판 CRUD
- 사용자 ID, 해수욕장 위치에 따른 검색 기능
- 댓글, 좋아요 기능
- Pagenation
### 4. 서핑클래스 예약
- 해수욕장에 따른 서핑클래스 예약 서비스 제공
- 예약 확인 및 결제 기능
### 5. 회원관리
- 로그인 / 로그아웃
- 회원가입 : 아이디 중복확인, 비밀번호 복잡성 요구, 비밀번호 시큐리티
- 회원정보 수정 : 비밀번호 변경, 회원 정보 변경
- 회원 탈퇴 : 참조 무결성 제약 조건 유지 및 처리

<br>


## ⏰ 개발 기간
2024/06/19 ~ 2024/07/02

<br>

## 👩‍💻 멤버 구성
- 허창범(팀장)
- 황준하(부팀장)
- 손수영
- 임다영  
- 박시은
- 김지은

<br>


## 📹 시연 영상
링크 위치
이미지를 클릭하면 영상을 시청할 수 있는 링크로 이동됩니다.

<br>

## 🖥 화면별 기능
![image](https://github.com/GSITM-Team3/spring-environment-monitoring/assets/144106932/a9c79da1-d590-41ca-bd2f-4d5ee1a8b192)
![image](https://github.com/GSITM-Team3/spring-environment-monitoring/assets/144106932/04efb51b-cf80-47fd-995c-5ca893f3ad2d)
![image](https://github.com/GSITM-Team3/spring-environment-monitoring/assets/144106932/454314ba-f7e7-41be-9067-00de14ba25dd)
![image](https://github.com/GSITM-Team3/spring-environment-monitoring/assets/144106932/04b0d606-3c74-40c2-bf2e-28a195c0dffb)
![image](https://github.com/GSITM-Team3/spring-environment-monitoring/assets/144106932/4c6c943c-7cf8-4328-9bda-e5b40bd12925)
![image](https://github.com/GSITM-Team3/spring-environment-monitoring/assets/144106932/4563829f-a5d4-46b5-a385-13b651018350)
