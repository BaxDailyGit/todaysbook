# 🖥 프로젝트 명 'TodaysBook'

### 목차
- [1. 프로젝트 소개](#1-프로젝트-소개)
- [2. 개발 인원 및 역할](#2-개발-인원-및-역할)
- [3. 프로젝트 일정](#3-프로젝트-일정)
- [4. 기술 스택](#4-기술스택)
- [5. 개발 일정](#5-개발-일정)
- [6. 아키텍처 설계도](#6-아키텍처-설계도)
- [7. DB 구성도](#7-DB-구성도)
- [8. 플로우 차트](#8-Flow-Chart)
- [11. UI](#11-UI)
- [12. 주요 기능](#12-주요-기능)
- [13. 프로젝트 구조](#13-프로젝트-구조)


# 1. 프로젝트 소개

todaysBook은 책을 사랑하는 사람들을 위한 새로운 e-커머스 플랫폼입니다.<br>
평소에 좋아하던 책들을 공유하고, 다른 사람들 혹은 AI의 추천을 받아보며 새로운 책을 발견해보세요!

<br><br>
[클릭하여 페이지를 방문하세요](http://43.203.7.96:8080/)
<br><br>
[프로젝트 시연 영상 보러가기]()
<br><br>

# 2. 개발 인원 및 역할

<table style="width: 100%;">
<tr>
    <td align="center" style="width: 25%;"><a href="https://github.com/fernandokkang"><img src="src/main/resources/static/images/readMe/sunghoon.jpg" width="130px;" alt=""></a></td>
    <td align="center" style="width: 25%;"><a href="https://github.com/dhktjr0204"><img src="src/main/resources/static/images/readMe/jiwon.jpg" width="130px;" alt=""></a></td>
    <td align="center" style="width: 25%;"><a href="https://github.com/kumacooma"><img src="src/main/resources/static/images/readMe/doehoon.jpg" width="130px;" alt=""></a></td>
</tr>
<tr>
    <td align="center"><a href="https://github.com/fernandokkang"><b>강성훈</b></a></td>
    <td align="center"><a href="https://github.com/dhktjr0204"><b>정지원</b></a></td>
    <td align="center"><a href="https://github.com/kumacooma"><b>이도훈</b></a></td>
</tr>
<tr>
    <td align="center">팀장</td>
    <td align="center">팀원</td>
    <td align="center">팀원</td>
</tr>
<tr> 
    <td align="center">Backend, Front</td>
    <td align="center">Backend</td>
    <td align="center">Backend</td>
</tr> 
<tr> 
    <td align="center">프로젝트 관리<br>프로젝트 발표<br>ERD 설계<br>aladin API를 이용한 책 데이터 수집 및 정제<br>UI/UX 디자인<br>디자인 파일 html 템플릿화<br>책 상세 정보 API<br>리뷰 작성 API<br>리뷰 추천/비추천 API<br>책 찜하기 API<br>아이템 기반 추천 시스템 설계<br>현재 책 기준 추천 책 제공 API<br>내가 찜한 책 기준 추천 책 제공 API<br>주문 내역 관리 API<br>매출 관리 API<br>차트를 통한 매출 시각화</td>
    <td align="center">ERD 설계<br>aladin api를 이용한 책 데이터 수집 및 정제<br>책 추천 리스트 API<br>추천 리스트 북마크 API<br>책 정렬 API<br>책 장르별 조회 API<br>책, 리스트 검색 API<br>주문 내역 및 배송 조회 API<br>유저 관리 API<br>신간 등록 API<br>배송 관리 API<br>책 수량 변경 및 수정 관리 API<br>JavaScript 개발(관리자, 추천 리스트, 페이징, 검색)<br>테스트 코드 작성<br>리드미 작성<br>플로우 차트 작성<br>요구사항 명세서 작성<br>프로젝트 배포</td>
    <td align="center"></td>
</tr> 
</table>
<table style="width: 100%;">
<tr>
    <td align="center" style="width: 25%;"><a href="https://github.com/JuwoenLee"><img src="src/main/resources/static/images/readMe/juweon.png" width="130px;" alt=""></a></td>
    <td align="center" style="width: 25%;"><a href="https://github.com/udidcs"><img src="src/main/resources/static/images/readMe/jeajung.jpg" width="130px;" alt=""></a></td>
</tr>
<tr>
    <td align="center"><a href="https://github.com/JuwoenLee"><b>이주원</b></a></td>
    <td align="center"><a href="https://github.com/udidcs"><b>변재정</b></a></td>
</tr>
<tr>
    <td align="center">팀원</td>
    <td align="center">팀원</td>
</tr>
<tr>
    <td align="center">Backend</td>
    <td align="center">Backend</td>
</tr> 
<tr> 
    <td align="center">로그인 API 개발<br>회원가입 API 개발<br>회원 정보 수정 API 개발<br>비밀번호 수정 API 개발<br>Spring Security<br>JavaScript 개발 (로그인, 회원가입, 정보 수정)</td>
    <td align="center">결제 API연동<br>API 연동을 위해 개발 회사에 질의 응답<br>결제 후 주문 생성 로직 작성<br>결제 페이지 자바스크립트 작성<br>세션을 활용한 데이터 전달<br>결제 페이지 정보 유지 오류 해결을 위한 html, css조작</td>
</tr> 
</table>

<br>

# 3. 프로젝트 일정

2024/04/24(수) ~ 2024/05/16(목)

<br>

# 4. 기술스택

### [기술 - FE]

<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">

### [기술 - BE]

<img  src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">

### [기술 - DB]

<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white">

### [기술 - Data Analysis]
<img src="https://img.shields.io/badge/mahout-ECD53F?style=for-the-badge&logo=apachehadoop&logoColor=white">

### [기술 - AI]

<img src="https://img.shields.io/badge/googlegemini-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white"> <img src="https://img.shields.io/badge/alan-66CCFF?style=for-the-badge&logoColor=white">

### [개발 도구]

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white">  <img src="https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white">  <img src="https://img.shields.io/badge/JIRA-0052CC?style=for-the-badge&logo=Jira&logoColor=white">

<br>

# 5. 개발 일정

![Architecture](/src/main/resources/static/images/readMe/일정표.png)

<br>

# 6. 아키텍처 설계도

![Architecture](/src/main/resources/static/images/readMe/architecture.png)

<br>

# 7. DB 구성도

![ERD](/src/main/resources/static/images/readMe/erd.png)

# 8. Flow Chart

![FLOWCHART](/src/main/resources/static/images/readMe/flowchart.png)

# 9. 요구사항 및 기능명세

# 10. API 명세서

### Admin
| URL                              | HTTP Method | 설명               |   로그인 권한 필요   |  작성자 권한 필요  | Admin 권한 
|----------------------------------|-------------|------------------|:-------------:|:-----------:|:--------:|
| '/admin/userlist'                | GET         | 회원 목록 조회         |       ✅       |             |     ✅     |
| '/admin/userlist/search'         | GET         | 회원 목록 검색         |       ✅       |             |     ✅     |
| '/admin/userlist'                | PUT         | 회원 등급 수정         |       ✅       |             |     ✅     |
| '/admin/userlist'                | DELETE      | 회원 삭제            |       ✅       |             |     ✅     |
| '/admin/stocklist'               | GET         | 책 재고 목록 조회       |       ✅       |             |     ✅     |
| '/admin/stocklist/search'        | GET         | 책 재고 검색          |       ✅       |             |     ✅     |
| '/admin/stocklist/sold-out'      | GET         | 책 재고 품절 목록 조회    |       ✅       |             |     ✅     |
| '/admin/stocklist'               | PUT         | 책 재고 수정          |       ✅       |             |     ✅     |
| '/admin/stocklist'               | DELETE      | 책 삭제             |       ✅       |             |     ✅     |
| '/admin/booklist'                | GET         | 전체 책 목록 조회       |       ✅       |             |     ✅     |
| '/admin/booklist/search'         | GET         | 전체 책 목록 검색       |       ✅       |             |     ✅     |
| '/admin/booklist/edit'           | GET         | 책 정보 수정 페이지      |       ✅       |             |     ✅     |
| '/admin/booklist/edit'           | PUT         | 책 정보 수정          |       ✅       |             |     ✅     |
| '/admin/book_registration'       | GET         | 신간 등록 페이지        |       ✅       |             |     ✅     |
| '/admin/book_registration/search' | GET         | 신간 검색            |       ✅       |             |     ✅     |
| '/admin/book_registration'       | PUT         | 신간 등록            |       ✅       |             |     ✅     |
| '/admin/sync'                    | GET         | 추천 정보 동기화        |       ✅       |             |     ✅     |
| '/admin/sales'                   | GET         | 매출 관리 페이지        |       ✅       |             |     ✅     |
| '/admin/sales_category'          | GET         | 카테고리 별 매출 관리 페이지 |       ✅       |             |     ✅     |
| '/admin/sales_book'              | GET         | 책 별 매출 관리 페이지    |       ✅       |             |     ✅     |
| '/admin/order'                   | GET         | 주문 목록 조회         |       ✅       |             |     ✅     |
| '/admin/order/{id}'              | GET         | 주문 상세 조회         |       ✅       |             |     ✅     |
| '/admin/delivery'                | GET         | 배송 목록 조회         |       ✅       |             |     ✅     |
| '/admin/delivery/search'         | GET         | 배송 검색            |       ✅       |             |     ✅     |
| '/admin/delivery'          | PUT         | 배송 상태 수정         |       ✅       |             |     ✅     |

# 11. UI

|                                   로그인 화면                                    |                                       회원가입 화면                                       |
|:---------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------:|
|        ![로그인 이미지](/src/main/resources/static/images/readMe/로그인.jpg)         |           ![회원가입 이미지](/src/main/resources/static/images/readMe/회원가입.jpg)            |
|                                  <b>메인 화면                                   |                                      <b>책 상세 화면                                     |
|         ![메인 이미지](/src/main/resources/static/images/readMe/메인.png)          |            ![책 상세 이미지](/src/main/resources/static/images/readMe/책상세.png)            |
|                                 <b>주문내역 화면                                  |                                    <b>내가 쓴 리뷰 화면                                    |
|       ![주문내역 이미지](/src/main/resources/static/images/readMe/주문내역.png)        |         ![내가 쓴 리뷰 이미지](/src/main/resources/static/images/readMe/내가쓴리뷰.png)          |
|                                <b>내가 찜한 책 화면                                |                                 <b>내가 찜한 추천 리스트 화면                                  |
|     ![내가 찜한 책 이미지](/src/main/resources/static/images/readMe/내가찜한책.png)      |         ![게시글 이미지](/src/main/resources/static/images/readMe/내가찜한추천리스트.png)          |
|                                  <b>결제 화면                                   |                                  <b>추천 리스트 만들기 화면                                   |
|        ![결제 이미지](/src/main/resources/static/images/readMe/결제페이지.png)        |       ![추천 리스트 만들기 이미지](/src/main/resources/static/images/readMe/리스트만들기.png)        |
|                               <b>회원 정보 수정 화면                                |                                    <b>비밀번호 수정 화면                                    |
|     ![회원 정보 수정 이미지](/src/main/resources/static/images/readMe/회원수정.png)      |         ![비밀번호 수정 이미지](/src/main/resources/static/images/readMe/비밀번호수정.png)         |
|                                 <b>장바구니 화면                                  |                                     <b>책 검색 화면                                      |
|       ![장바구니 이미지](/src/main/resources/static/images/readMe/장바구니.png)        |            ![책 검색 이미지](/src/main/resources/static/images/readMe/책검색.png)            |
|                               <b>앨런 실시간 검색 화면                               |                                <b>앨런 실시간 검색(결과물) 화면                                 |
|  ![앨런 실시간 검색 이미지](/src/main/resources/static/images/readMe/앨런실시간검색서비스.png)  | ![앨런 실시간 검색(결과물) 이미지](/src/main/resources/static/images/readMe/앨런실시간검색서비스(결과물).png) |
|                             <b>내가 만든 추천 리스트 화면                              |                                     <b>결제 모듈 화면                                     |
| ![내가 만든 추천 리스트 이미지](/src/main/resources/static/images/readMe/내가만든추천리스트.png) |           ![결제 모듈 이미지](/src/main/resources/static/images/readMe/결제모듈.png)           |
|                                 <b>결제 완료 화면                                 |                                 <b>주문 내역(관리자) 조회 화면                                 |
|       ![결제 완료 이미지](/src/main/resources/static/images/readMe/결제완료.png)       |      ![주문 내역(관리자) 이미지](/src/main/resources/static/images/readMe/관리자주문내역조회.png)      |
|                               <b>월 별 매출 조회 화면                               |                                 <b> 카테고리 별 매출 조회 화면                                 |
|    ![월 별 매출 이미지](/src/main/resources/static/images/readMe/매출조회(월별).png)     |      ![카테고리 별 매출 이미지](/src/main/resources/static/images/readMe/카테고리별매출조회.png)       |
|                                 <b>회원 조회 화면                                 |                                     <b>재고 조회 화면                                     |
|       ![회원조회 이미지](/src/main/resources/static/images/readMe/회원조회.png)        |           ![재고 조회 이미지](/src/main/resources/static/images/readMe/재고조회.png)           |
|                                 <b>배송 조회 화면                                 |                                     <b>신간 등록 화면                                     |
|       ![배송 조회 이미지](/src/main/resources/static/images/readMe/배송관리.png)       |      ![앨런 실시간 검색(결과물) 이미지](/src/main/resources/static/images/readMe/신간등록.png)       |


<br><br>

# 12. 주요 기능

## (1). 메인 페이지
### [gemini 추천 기능]
- 매일 오전 9시에에 gemini가 추천하는 책으로 업데이트됩니다.
- 추천 책 중 DB에 없는 책들은 aladin API를 사용하여 책을 등록합니다.

### [찜한 책 조회 기능]
- 내가 찜한 책들을 한 눈에 볼 수 있습니다.
- 찜한 책들과 사용자들의 리뷰를 기반으로 유사한 책들을 추천해줍니다.

### [사용자들이 만든 리스트 조회 기능]
- 사용자들이 만든 추천 리스트가 랜덤으로 3개 노출됩니다.
- 마음에 드는 리스트를 북마크하여 마이페이지에서 조회 가능합니다.
- 이 기능을 통해 다른 사용자들이 추천한 책을 탐색하고, 새로운 책을 발견할 수 있습니다.

<br>

## (2). Alan 실시간 검색 페이지
![앨런 검색 이미지](/src/main/resources/static/images/readMe/앨런검색.gif)
### [Alan 실시간 검색 기능]
- alan api를 사용하여 alan에게 책을 직접 물어봄으로써 책을 추천 받을 수 있습니다.
- alan api가 입력된 질문을 분석하여 사용자에게 가장 적합한 책을 추천합니다.

<br>

## (3). 책 조회 페이지
![책 조회 이미지](/src/main/resources/static/images/readMe/책조회.gif)
### [장르별 조회 기능]
- 장르 별로 책을 조회 할 수 있습니다. 자기계발, 소설/시/희곡, 외국어 등 다양한 주제의 책을 장르별로 쉽게 찾아 볼 수 있습니다.
- 각 장르는 가나다순, 판매순, 리뷰순으로 정렬하여 조회할 수 있습니다.

### [검색 기능]
- 책 제목 및 저자로 책을 검색할 수 있습니다.

<br>

## (4). 책 상세 페이지
![책 상세 이미지](/src/main/resources/static/images/readMe/책상세.gif)
- 책 상세 페이지에는 책의 제목, 저자, 출판사, 출판일, 가격을 확인할 수 있습니다.

### [연관된 책 추천 기능]
- 리뷰가 등록된 책은 추천 알고리즘을 이용하여 그것과 연관된 책을 추천해줍니다.
- 추천 알고리즘은 아이템 기반 추천을 사용하였으며, 책 리뷰에 달린 평점을 기반으로 유사도를 계산하여 사용자들에게 최대 5개의 책을 추천해줍니다.

### [리뷰 기능]
- 책 상세 페이지에서 리뷰를 등록할 수 있습니다. 평점은 1~5점까지 부여할 수 있으며, 최대 500자까지의 내용을 작성할 수 있습니다.
- 리뷰에는 추천, 비추천을 할 수 있습니다. 리뷰를 추천순, 최신순으로 정렬하여 조회할 수 있습니다.

<br>

## (5). 추천 리스트 페이지

- 다른 사용자에게 추천하고 싶은 책들을 리스트로 만들 수 있습니다.
- 만든 추천 리스트들은 메인페이지에 랜덤으로 3개가 노출됩니다
- 북마크를 통해 다른 사람이 만든 추천 리스트를 저장할 수 있습니다.저장된 추천 리스트는 마이페이지에서 언제든지 확인할 수 있습니다.

<br>

## (6). 구매 페이지

### [유저 등급 및 마일리지 적립]
- 유저 등급에 따라 쌓이는 마일리지가 달라집니다. 다이아몬드 회원은 구매금액의 10%를, 골드 회원은 7%를, 실버 회원은 5%를, 브론즈 회원은는 3%를 적립받습니다.
- 유저의 등급은 매월 1일에 업데이트됩니다. 한 달 동안 30만원 이상 구매시 다이아몬드, 10만원 이상 구매 시 골드, 5만원 이상 구매 시 실버, 그 이하로 구매 시 브론즈 승급되거나 강등됩니다.

### [구매 기능]
- 책 상세 페이지에서 '장바구니' 혹은 '구매하기'를 클릭하여 책을 구매할 수 있습니다.
- 주소 입력란에는 마이페이지에 저장된 주소를 불러올 수 있으며, 새로운 주소를 등록할 수도 있습니다.
- 카카오 우편번호 찾기 api를 이용하여 우편번호를 가져올 수 있습니다.
- 결제 시 보유한 마일리지를 최대 책의 가격의 30%까지 사용하여 할인을 받을 수 있습니다.
- 결제는 카드 결제, 무통장 입금이 가능합니다.

<br>

## (7). 마이 페이지

- 보유한 마일리지 확인: 현재 보유한 마일리지를 확인할 수 있습니다.
- 주문 내역 조회: 사용자가 이전에 주문한 내역을 확인할 수 있습니다. 주문 번호를 클릭하면 해당 주문의 상세 정보를 확인할 수 있습니다. 또한 송장 번호를 클릭하면 현재 배송 상태를 확인할 수 있습니다.
- 장바구니: 장바구니에 담겨 있는 상품을 확인하고 주문을 진행할 수 있습니다.
- 내가 쓴 리뷰 조회: 사용자가 작성한 리뷰를 확인할 수 있습니다.
- 내가 찜한 책 조회: 사용자가 찜한 책을 확인할 수 있습니다.
- 내가 찜한 추천리스트 조회: 사용자가 찜한 추천리스트를 확인할 수 있습니다.
- 내가 만든 추천리스트 조회: 사용자가 만든 추천리스트를 확인하고, 새로운 추천리스트를 만들 수 있습니다.

<br>

## (8). 관리자 페이지

### [매출 조회 기능]
- 주문 내역 조회: 하루 동안의 주문 내역을 조회할 수 있습니다. 날짜를 변경하여 각각의 날의 주문 내역을 조회 할 수 있습니다.
- 월 별 매출 조회: 월별 매출을 확인하여 년간의 매출 흐름을 파악할 수 있습니다.
- 카테고리 별 매출 조회: 각 장르의 판매량을 확인하여 어떤 장르가 인기 있는지 파악할 수 있습니다.
- 상품 별 매출 조회: 각 상품의 판매량을 확인하여 인기 상품을 파악할 수 있습니다.

### [관리 기능]
- 회원 관리: 유저 등급을 조정하고, 회원의 정보를 조회하고 관리할 수 있습니다.
- 재고 관리: 책의 재고량을 확인하고 관리할 수 있습니다. 재고 없는 책을 쉽게 파악할 수 있습니다.
- 배송 관리: 주문의 배송 상태를 확인하고 관리할 수 있습니다. 배송 준비중, 배송 중, 배송 완료 등의 상태를 변경할 수 있습니다.
- 신간 등록: Aladin API를 이용하여 원하는 책을 검색하고, 선택하여 신간으로 등록할 수 있습니다.
- 책 정보 수정: 책의 제목, 저자, 출판사, 가격, 설명 등의 정보를 수정할 수 있습니다.
- 관리자 정보 수정: 관리자의 닉네임이나 비밀번호를 변경할 수 있습니다.

<br><br>

# 13. 프로젝트 구조

```
📦 
├─ .github
│  └─ workflows
│     └─ deploy.yml
├─ .gitignore
├─ README.md
├─ appspec.yml
├─ build.gradle
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ scripts
│  ├─ start.sh
│  └─ stop.sh
├─ settings.gradle
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ example
   │  │        └─ todaysbook
   │  │           ├─ TodaysbookApplication.java
   │  │           ├─ config
   │  │           │  ├─ GeminiRestTemplateConfig.java
   │  │           │  └─ SecurityConfig.java
   │  │           ├─ constant
   │  │           │  └─ Constant.java
   │  │           ├─ controller
   │  │           │  ├─ AdminController.java
   │  │           │  ├─ AlanChatController.java
   │  │           │  ├─ BookDetailController.java
   │  │           │  ├─ BookMarkController.java
   │  │           │  ├─ CartController.java
   │  │           │  ├─ CategoryController.java
   │  │           │  ├─ FavoriteBookController.java
   │  │           │  ├─ GeminiApiCallController.java
   │  │           │  ├─ GeminiRecommendBooksController.java
   │  │           │  ├─ MainController.java
   │  │           │  ├─ MypageController.java
   │  │           │  ├─ PaymentController.java
   │  │           │  ├─ RecommendListController.java
   │  │           │  ├─ ReviewController.java
   │  │           │  ├─ SalesController.java
   │  │           │  ├─ SearchController.java
   │  │           │  ├─ UserController.java
   │  │           │  ├─ UserViewController.java
   │  │           │  └─ ViewController.java
   │  │           ├─ domain
   │  │           │  ├─ CategoryEnum.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ AdminUserDto.java
   │  │           │  │  ├─ AlanChatResponse.java
   │  │           │  │  ├─ BookDetail.java
   │  │           │  │  ├─ BookDetailDto.java
   │  │           │  │  ├─ BookDto.java
   │  │           │  │  ├─ CartRequestDto.java
   │  │           │  │  ├─ CustomUserDetails.java
   │  │           │  │  ├─ DailyOrderDto.java
   │  │           │  │  ├─ DeliveryDto.java
   │  │           │  │  ├─ FavoriteBookDTO.java
   │  │           │  │  ├─ GeminiRecommendApiRequest.java
   │  │           │  │  ├─ GeminiRecommendApiResponse.java
   │  │           │  │  ├─ GeminiRecommendBookDto.java
   │  │           │  │  ├─ LoginRequest.java
   │  │           │  │  ├─ LoginRequestDto.java
   │  │           │  │  ├─ MyPageOrderDto.java
   │  │           │  │  ├─ MyReview.java
   │  │           │  │  ├─ Order.java
   │  │           │  │  ├─ OrderDetailDTO.java
   │  │           │  │  ├─ OrderInfo.java
   │  │           │  │  ├─ PaymentAddressAndMileageInfo.java
   │  │           │  │  ├─ PaymentBookInfoDto.java
   │  │           │  │  ├─ RecommendBookDto.java
   │  │           │  │  ├─ RecommendListCreateRequestDto.java
   │  │           │  │  ├─ RecommendListDetailDto.java
   │  │           │  │  ├─ RecommendListDetailWithBookMarkDto.java
   │  │           │  │  ├─ RecommendListDto.java
   │  │           │  │  ├─ RecommendListUpdateRequestDto.java
   │  │           │  │  ├─ RecommendListWithBookMarkDto.java
   │  │           │  │  ├─ Review.java
   │  │           │  │  ├─ ReviewRequestDto.java
   │  │           │  │  ├─ SalesDetailDto.java
   │  │           │  │  ├─ SalesDto.java
   │  │           │  │  ├─ SimpleReview.java
   │  │           │  │  ├─ UserRequestDto.java
   │  │           │  │  └─ UserResponseDto.java
   │  │           │  └─ entity
   │  │           │     ├─ Book.java
   │  │           │     ├─ BookMark.java
   │  │           │     ├─ Cart.java
   │  │           │     ├─ CartBook.java
   │  │           │     ├─ Category.java
   │  │           │     ├─ Delivery.java
   │  │           │     ├─ Dislike.java
   │  │           │     ├─ FavoriteBook.java
   │  │           │     ├─ GeminiRecommendBook.java
   │  │           │     ├─ Like.java
   │  │           │     ├─ OrderBook.java
   │  │           │     ├─ Orders.java
   │  │           │     ├─ Review.java
   │  │           │     ├─ Role.java
   │  │           │     ├─ User.java
   │  │           │     ├─ UserRecommendBook.java
   │  │           │     └─ UserRecommendList.java
   │  │           ├─ exception
   │  │           │  ├─ GlobalExceptionHandler.java
   │  │           │  ├─ admin
   │  │           │  │  ├─ AuthorLengthOverException.java
   │  │           │  │  ├─ DescriptionLengthOverException.java
   │  │           │  │  ├─ EmptyAuthorException.java
   │  │           │  │  ├─ EmptyPriceException.java
   │  │           │  │  ├─ EmptyPublisherException.java
   │  │           │  │  └─ PublisherLengthOverException.java
   │  │           │  ├─ aladinApi
   │  │           │  │  └─ NotValidBook.java
   │  │           │  ├─ book
   │  │           │  │  └─ DuplicateBookException.java
   │  │           │  ├─ bookMark
   │  │           │  │  ├─ AlreadyBookmarkedException.java
   │  │           │  │  └─ NotBookmarkedYetException.java
   │  │           │  ├─ delivery
   │  │           │  │  └─ DeliveryNotFoundException.java
   │  │           │  ├─ order
   │  │           │  │  └─ OrderNotFoundException.java
   │  │           │  ├─ recommendList
   │  │           │  │  ├─ BookNotFoundException.java
   │  │           │  │  ├─ EmptyTitleException.java
   │  │           │  │  ├─ RecommendListLengthOverException.java
   │  │           │  │  ├─ RecommendListNotFoundException.java
   │  │           │  │  └─ TitleLengthOverException.java
   │  │           │  ├─ review
   │  │           │  │  ├─ ContentEmptyException.java
   │  │           │  │  └─ ContentLengthOverException.java
   │  │           │  └─ user
   │  │           │     ├─ EmptyAddressException.java
   │  │           │     ├─ EmptyEmailException.java
   │  │           │     ├─ EmptyNameException.java
   │  │           │     ├─ EmptyNicknameException.java
   │  │           │     ├─ EmptyPasswordException.java
   │  │           │     ├─ EmptyZipcodeException.java
   │  │           │     ├─ NicknameLengthOverException.java
   │  │           │     ├─ NicknameLengthUnderException.java
   │  │           │     ├─ NotLoggedInException.java
   │  │           │     ├─ NotSamePasswordException.java
   │  │           │     ├─ UserNotFoundException.java
   │  │           │     ├─ UserValidateException.java
   │  │           │     ├─ WrongEmailPatternException.java
   │  │           │     ├─ WrongPasswordPatternException.java
   │  │           │     └─ WrongZipcodePatternException.java
   │  │           ├─ repository
   │  │           │  ├─ BookDetailMapper.java
   │  │           │  ├─ BookMarkRepository.java
   │  │           │  ├─ BookRepository.java
   │  │           │  ├─ CartBookRepository.java
   │  │           │  ├─ CartRepository.java
   │  │           │  ├─ DeliveryRepository.java
   │  │           │  ├─ FavoriteBookMapper.java
   │  │           │  ├─ FavoriteBookRepository.java
   │  │           │  ├─ GeminiRecommendBookRepository.java
   │  │           │  ├─ OrderBookRepository.java
   │  │           │  ├─ OrderMapper.java
   │  │           │  ├─ OrderRepository.java
   │  │           │  ├─ RecommendBookMapper.java
   │  │           │  ├─ RecommendListMapper.java
   │  │           │  ├─ ReviewMapper.java
   │  │           │  ├─ ReviewRepository.java
   │  │           │  ├─ SalesMapper.java
   │  │           │  ├─ UserMapper.java
   │  │           │  ├─ UserRecommendBookRepository.java
   │  │           │  ├─ UserRecommendListRepository.java
   │  │           │  └─ UserRepository.java
   │  │           ├─ service
   │  │           │  ├─ AdminService.java
   │  │           │  ├─ AdminServiceImpl.java
   │  │           │  ├─ AlanChatService.java
   │  │           │  ├─ BookDetailService.java
   │  │           │  ├─ BookDetailServiceImpl.java
   │  │           │  ├─ BookMarkService.java
   │  │           │  ├─ BookMarkServiceImpl.java
   │  │           │  ├─ CartService.java
   │  │           │  ├─ CategoryService.java
   │  │           │  ├─ CategoryServiceImpl.java
   │  │           │  ├─ FavoriteBookService.java
   │  │           │  ├─ FavoriteBookServiceImpl.java
   │  │           │  ├─ GeminiRecommendBookService.java
   │  │           │  ├─ OrderService.java
   │  │           │  ├─ OrderServiceImpl.java
   │  │           │  ├─ PaymentService.java
   │  │           │  ├─ PaymentServiceImpl.java
   │  │           │  ├─ RecommendBookService.java
   │  │           │  ├─ RecommendBookServiceImpl.java
   │  │           │  ├─ RecommendListService.java
   │  │           │  ├─ RecommendListServiceImpl.java
   │  │           │  ├─ ReviewService.java
   │  │           │  ├─ ReviewServiceImpl.java
   │  │           │  ├─ SalesService.java
   │  │           │  ├─ SalesServiceImpl.java
   │  │           │  ├─ SearchService.java
   │  │           │  ├─ SearchServiceImpl.java
   │  │           │  ├─ UserService.java
   │  │           │  └─ upgradeUserRoleService.java
   │  │           ├─ util
   │  │           │  ├─ AladinApi.java
   │  │           │  ├─ Pagination.java
   │  │           │  └─ UserChecker.java
   │  │           └─ validate
   │  │              ├─ AdminUpdateBookValidator.java
   │  │              ├─ RecommendListCreateValidator.java
   │  │              ├─ RecommendListUpdateValidator.java
   │  │              ├─ ReviewCreateValidator.java
   │  │              ├─ UserRegisterValidator.java
   │  │              ├─ UserUpdateAddressValidator.java
   │  │              ├─ UserUpdateNicknameValidator.java
   │  │              └─ UserUpdatePasswordValidator.java
   │  └─ resources
   │     ├─ data
   │     │  └─ rating.csv
   │     ├─ mappers
   │     │  ├─ book
   │     │  │  ├─ bookDetail.xml
   │     │  │  └─ recommendBook.xml
   │     │  ├─ favorite
   │     │  │  └─ favoriteBook.xml
   │     │  ├─ order
   │     │  │  └─ order.xml
   │     │  ├─ recommendList
   │     │  │  └─ recommendList.xml
   │     │  ├─ review
   │     │  │  └─ review.xml
   │     │  ├─ sales
   │     │  │  └─ sales.xml
   │     │  └─ user
   │     │     └─ user.xml
   │     ├─ static
   │     │  ├─ css
   │     │  │  ├─ admin
   │     │  │  │  ├─ book-registration.css
   │     │  │  │  ├─ booklist-edit-form.css
   │     │  │  │  ├─ booklist.css
   │     │  │  │  ├─ common.css
   │     │  │  │  ├─ delivery.css
   │     │  │  │  ├─ gemini-recommend-book.css
   │     │  │  │  ├─ order-detail.css
   │     │  │  │  ├─ order.css
   │     │  │  │  ├─ sales-book.css
   │     │  │  │  ├─ sales.css
   │     │  │  │  ├─ stocklist.css
   │     │  │  │  ├─ update-info.css
   │     │  │  │  ├─ update-password.css
   │     │  │  │  └─ userlist.css
   │     │  │  ├─ alan
   │     │  │  │  └─ chat.css
   │     │  │  ├─ book
   │     │  │  │  ├─ category.css
   │     │  │  │  ├─ detail.css
   │     │  │  │  └─ search.css
   │     │  │  ├─ cart
   │     │  │  │  └─ list.css
   │     │  │  ├─ common.css
   │     │  │  ├─ footer.css
   │     │  │  ├─ header.css
   │     │  │  ├─ index.css
   │     │  │  ├─ list
   │     │  │  │  ├─ detail.css
   │     │  │  │  ├─ list.css
   │     │  │  │  └─ listForm.css
   │     │  │  ├─ payment
   │     │  │  │  ├─ info.css
   │     │  │  │  ├─ pay.css
   │     │  │  │  ├─ style.css
   │     │  │  │  └─ success.css
   │     │  │  ├─ sidebar-admin.css
   │     │  │  ├─ sidebar-user.css
   │     │  │  └─ user
   │     │  │     ├─ login.css
   │     │  │     ├─ mypage
   │     │  │     │  ├─ create-recommendlist.css
   │     │  │     │  ├─ delivery.css
   │     │  │     │  ├─ favorite-book.css
   │     │  │     │  ├─ mileage.css
   │     │  │     │  ├─ my-recommendlist.css
   │     │  │     │  ├─ orderlist-detail.css
   │     │  │     │  ├─ orderlist.css
   │     │  │     │  ├─ review.css
   │     │  │     │  ├─ update-info.css
   │     │  │     │  ├─ update-password.css
   │     │  │     │  └─ users-recommendlist.css
   │     │  │     └─ registration.css
   │     │  ├─ favicon.ico
   │     │  ├─ images
   │     │  │  ├─ alan-2.gif
   │     │  │  ├─ alan.gif
   │     │  │  ├─ alan.png
   │     │  │  ├─ alan_chat.png
   │     │  │  ├─ blank_star.png
   │     │  │  ├─ dislike.png
   │     │  │  ├─ dragndrop.png
   │     │  │  ├─ error
   │     │  │  │  └─ 404error.gif
   │     │  │  ├─ gemini-api-call-btn.png
   │     │  │  ├─ github-icon.png
   │     │  │  ├─ like.png
   │     │  │  ├─ login-background.png
   │     │  │  ├─ logo.png
   │     │  │  ├─ search.png
   │     │  │  ├─ star.png
   │     │  │  ├─ triangle-down.svg
   │     │  │  └─ user_chat.png
   │     │  └─ js
   │     │     ├─ admin
   │     │     │  ├─ bookRegistration
   │     │     │  │  ├─ bookRegistration.js
   │     │     │  │  └─ page.js
   │     │     │  ├─ booklist
   │     │     │  │  ├─ booklist.js
   │     │     │  │  ├─ editForm.js
   │     │     │  │  └─ page.js
   │     │     │  ├─ delivery
   │     │     │  │  ├─ delivery.js
   │     │     │  │  └─ page.js
   │     │     │  ├─ geminiRecommendBook
   │     │     │  │  └─ gemini-recommennd-book.js
   │     │     │  ├─ order
   │     │     │  │  ├─ date.js
   │     │     │  │  └─ page.js
   │     │     │  ├─ sales
   │     │     │  │  ├─ category-chart.js
   │     │     │  │  ├─ chart.js
   │     │     │  │  └─ page.js
   │     │     │  ├─ search.js
   │     │     │  ├─ stocklist
   │     │     │  │  ├─ page.js
   │     │     │  │  └─ stocklist.js
   │     │     │  ├─ sync.js
   │     │     │  └─ userlist
   │     │     │     ├─ page.js
   │     │     │     └─ userlist.js
   │     │     ├─ alan
   │     │     │  └─ alan-chat.js
   │     │     ├─ book
   │     │     │  ├─ categoryPage.js
   │     │     │  └─ detail.js
   │     │     ├─ cart
   │     │     │  ├─ list.js
   │     │     │  └─ listchild.js
   │     │     ├─ list
   │     │     │  ├─ dragAndDrop.js
   │     │     │  ├─ page.js
   │     │     │  ├─ search.js
   │     │     │  ├─ submit.js
   │     │     │  └─ titleWordCount.js
   │     │     ├─ modifyInfo.js
   │     │     ├─ modifyPassword.js
   │     │     ├─ mypage
   │     │     │  ├─ bookMark.js
   │     │     │  ├─ myBookMarkRecommendList.js
   │     │     │  ├─ myRecommendList.js
   │     │     │  ├─ page.js
   │     │     │  └─ withdraw.js
   │     │     ├─ payment
   │     │     │  ├─ daum_address_api.js
   │     │     │  └─ info.js
   │     │     ├─ review
   │     │     │  └─ review.js
   │     │     ├─ search
   │     │     │  ├─ book
   │     │     │  │  └─ page.js
   │     │     │  ├─ list
   │     │     │  │  └─ page.js
   │     │     │  └─ search.js
   │     │     ├─ user
   │     │     │  ├─ login.js
   │     │     │  └─ registration.js
   │     │     └─ wordCount.js
   │     └─ templates
   │        ├─ admin
   │        │  ├─ book-edit-form.html
   │        │  ├─ book-registration.html
   │        │  ├─ booklist.html
   │        │  ├─ delivery.html
   │        │  ├─ gemini-recommend-book.html
   │        │  ├─ order-detail.html
   │        │  ├─ order.html
   │        │  ├─ sales-book.html
   │        │  ├─ sales-category.html
   │        │  ├─ sales.html
   │        │  ├─ stocklist.html
   │        │  ├─ update-info.html
   │        │  ├─ update-password.html
   │        │  └─ userlist.html
   │        ├─ alan
   │        │  └─ chat.html
   │        ├─ book
   │        │  ├─ categoryBook.html
   │        │  ├─ detail.html
   │        │  ├─ review.html
   │        │  ├─ search.html
   │        │  └─ search_list.html
   │        ├─ cart
   │        │  └─ list.html
   │        ├─ error
   │        │  └─ 404.html
   │        ├─ fragments
   │        │  ├─ footer.html
   │        │  ├─ head.html
   │        │  ├─ header.html
   │        │  ├─ page.html
   │        │  ├─ recommend-list-body.html
   │        │  ├─ recommend-list-header.html
   │        │  ├─ sidebar-admin.html
   │        │  └─ sidebar-user.html
   │        ├─ index.html
   │        ├─ layout
   │        │  └─ layout.html
   │        ├─ login.html
   │        ├─ payment
   │        │  ├─ fail.html
   │        │  ├─ info.html
   │        │  ├─ pay_card.html
   │        │  ├─ pay_virtual.html
   │        │  └─ success.html
   │        ├─ recommendList
   │        │  └─ recommend-list-detail.html
   │        ├─ registration.html
   │        └─ user
   │           └─ mypage
   │              ├─ create-recommendlist.html
   │              ├─ delivery.html
   │              ├─ favorite-book.html
   │              ├─ mileage.html
   │              ├─ my-recommendlist.html
   │              ├─ orderlist-detail.html
   │              ├─ orderlist.html
   │              ├─ review.html
   │              ├─ update-info.html
   │              ├─ update-password.html
   │              ├─ update-recommendlist.html
   │              └─ users-recommendlist.html
   └─ test
      └─ java
         └─ com
            └─ example
               └─ todaysbook
                  ├─ TodaysbookApplicationTests.java
                  ├─ controller
                  │  ├─ AdminControllerTest.java
                  │  ├─ BookMarkControllerTest.java
                  │  ├─ FavoriteBookControllerTest.java
                  │  ├─ RecommendListControllerTest.java
                  │  ├─ ReviewControllerTest.java
                  │  └─ SearchControllerTest.java
                  └─ service
                     ├─ AdminServiceImplTest.java
                     ├─ BookMarkServiceImplTest.java
                     ├─ FavoriteBookServiceImplTest.java
                     ├─ OrderServiceImplTest.java
                     ├─ RecommendBookServiceImplTest.java
                     ├─ RecommendListServiceImplTest.java
                     ├─ ReviewServiceImplTest.java
                     └─ SearchServiceImplTest.java
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)


