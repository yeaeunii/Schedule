# 🍎 


## 서비스 소개
* 서비스명: 위치스 모바일 회의실 실시간 예약관리 앱
* 서비스설명: 인증된 직원들만 회의실 예약, 조회가 가능하며, 기존 예약자만 예약취소 할 수 있는 실시간 회의실 관리 앱
<br>

## 프로젝트 기간
2023.11.13 ~ 2023.11.24 (2주)
<br>

## 1️⃣ 주요 기능
* 카카오 로그인 인증
* 회의실 에약 조회
* 회의실 예약 취소
* 회의실 예약 등록
<br>

## 2️⃣ 기술스택
<table>
    <tr>
        <th>구분</th>
        <th>내용</th>
    </tr>
    <tr>
        <td>사용언어</td>
        <td>
           <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white"/>
        </td>
    </tr>
    <tr>
        <td>라이브러리</td>
        <td>
            <img src="https://img.shields.io/badge/Kakao-FFCD00?style=for-the-badge&logo=Kakao&logoColor=white"/>
        </td>
    </tr>
    <tr>
        <td>개발도구</td>
        <td>
       <img src="https://img.shields.io/badge/Androidstudio-3DDC84?style=for-the-badge&logo=Androidstudio&logoColor=white"/>
        </td>
    </tr>
    <tr>
        <td>서버환경</td>
        <td>
            <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"/> 
            <img src="https://img.shields.io/badge/Apache Tomcat 9.0-D22128?style=for-the-badge&logo=Apache Tomcat&logoColor=white"/> 
        </td>
    </tr>
    <tr>
        <td>데이터베이스</td>
        <td>
            <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>
        </td>
    </tr>
    <tr>
        <td>협업도구</td>
        <td>
            <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"/>
            <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"/>
        </td>
    </tr>
      <tr>
        <td>인프라구조</td>
        <td>
         <img src="https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white" > 
        </td>
    </tr>
     <tr>
        <td>프레임워크</td></td>
        <td>
            <img src="https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=JSON&logoColor=white"/>
        </td>
    </tr>
</table>


<br>


## 3️⃣ 화면 구성

### 🍎SPLASH
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/aa52689e-a2dc-442a-b135-82b8bdcb1d26" alt="스플래쉬" width="300" height="600">
<br>

### 🍎로그인/로그아웃
- 사용자에게 카카오톡 앱이 있으면 앱과 연결되어 바로 로그인이 가능함 <br>
- 사용자는 앱에 연결된 계정이 아닌 다른 이메일로도 로그인이 가능함<br>
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/4c2b681f-52b5-4dc2-bb2b-8741a4cf2a3f" alt="스플래쉬" width="300" height="600">
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/56fb5814-a016-4677-9da1-fe804faa059d" alt="스플래쉬" width="300" height="600">
<br>

### 🍎메인화면
 -오늘 날짜가 먼저 원으로 표시되고, 다른 날짜를 클릭시 진한 검정색으로 표시<br>
 -예약된 일정이 있는 날짜는 작은점으로 표시 됨<br>
 -달력에서 클릭한 날짜의 예약 정보(시간, 회의내용 및 인원, 부서, 신청인)가 제공<br>
 -item 어떤 부분을 눌러도 예약 상세 조회 가능
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/f98b1a4a-7901-4476-97c5-bc81ded14c5d" alt="스플래쉬" width="300" height="600">
<br>

### 🍎예약상세조회화면
- 클릭한 날짜 해당 일정의 예약일, 예약시간 시작시간 – 종료시간), 사용인수, 신청인, 부서,회의내용 및 참여인원 상세 내용을 보여줌<br>
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/be1570a3-3f58-4561-9a9d-963a40309d9f" alt="스플래쉬" width="300" height="600">
<br>

### 🍎예약취소화면 
-취소인, 취소사유 입력란이 있는 Dialog 띄워줌<br>
- 예약할 당시 신청인 로그인ID와 같은 ID일 경우에만 취소가 가능함<br>
   -다를 경우, “기존 신청인이 아닙니다” Toast창 보여주고 예악 취소가 되지않음<br>
   -ID가 같을 경우, 다이얼로그가 꺼지면서 “예약이 취소 되었습니다” Toast창 보여주고 예약 취소 됨<br>
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/edd2e67d-9299-4d25-8517-ca78ab5ee53d" alt="스플래쉬" width="300" height="600">
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/e05ac0e4-675f-4f9b-9e60-083d862b6ba3" alt="스플래쉬" width="300" height="600">
<br>

### 🍎예약신청화면
- 클릭한 날짜가 예약일로 보여지고, 예약시간 (시작시간 – 종료시간),사용인수,신청인, 부서,회의내용 및 참여인원 상세 정보 입력란 있음<br>
<img src="https://github.com/yeaeunii/Schedule/assets/132533622/280c7a6e-8b4c-4256-ad92-389dad08f4de" alt="예약신청화면" width="300" height="600">
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/7231a6d2-8970-43d6-a597-8bed11335b42" alt="예약신청완료" width="300" height="600">
<br>

##### ▶️예외처리 
-이미 지난 날짜 신청시, “지난 날짜는 예약이 불가합니다”,<br>
-이미 예약된 시간 신청시, “이미 예약된 시간입니다”,<br>
-필수값 미입력 신청시, “필수값을 입력해주세요”  Toast창으로 보여주고 예약 안됨<br>
-모두 충족시, “예약이 완료 되었습니다” Toast창으로 보여주고 메인화면으로 이동<br>
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/4bbf4df4-947c-47c9-8b01-ff95452316e6" alt="이미에약된날짜" width="300" height="600">
<img src ="https://github.com/yeaeunii/Schedule/assets/132533622/98763015-e6f4-4b44-b957-6ea52fd1d831" alt="이미예약된시간" width="300" height="600">

<br>
