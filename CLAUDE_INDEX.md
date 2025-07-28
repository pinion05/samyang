# Samyang 농업 플랫폼 - 구조화된 코드베이스 인덱스

## 🏗️ 프로젝트 개요

**프로젝트명**: Samyang 농업 관리 플랫폼  
**기술 스택**: Spring Boot 3.5.3 + MyBatis + MySQL + JSP  
**아키텍처**: MVC 패턴 + 계층형 아키텍처  
**빌드 도구**: Gradle  
**언어**: Java 17  

---

## 📁 디렉토리 구조 인덱스

### 🌟 루트 디렉토리
```
C:/dev/samyang/
├── 📋 build.gradle              # Gradle 빌드 설정
├── 📋 settings.gradle           # Gradle 프로젝트 설정
├── 📋 gradlew                   # Gradle 래퍼 스크립트 (Unix)
├── 📋 gradlew.bat               # Gradle 래퍼 스크립트 (Windows)
├── 📋 README.md                 # 프로젝트 문서
├── 📋 HELP.md                   # 도움말 문서
├── 📋 app.log                   # 애플리케이션 로그
├── 📁 gradle/wrapper/           # Gradle 래퍼 파일들
├── 📁 build/                    # 빌드 산출물
├── 📁 bin/                      # 컴파일된 클래스 파일
└── 📁 src/                      # 소스 코드 루트
```

### 🔧 소스 코드 구조 (src/)
```
src/
├── 📁 main/
│   ├── 📁 java/com/farm404/samyang/
│   │   ├── 🎯 SamyangApplication.java      # Spring Boot 메인 클래스
│   │   ├── 🎯 HelloSpringBoot.java         # 테스트용 클래스
│   │   ├── 📁 controller/                   # 컨트롤러 계층
│   │   ├── 📁 dto/                         # 데이터 전송 객체
│   │   ├── 📁 service/                     # 서비스 계층
│   │   ├── 📁 mapper/                      # MyBatis 매퍼 인터페이스
│   │   └── 📁 exception/                   # 예외 처리
│   ├── 📁 resources/
│   │   ├── 🔧 application.yml              # 메인 설정 파일
│   │   ├── 🔧 application-local.yml        # 로컬 환경 설정
│   │   ├── 📁 mapper/                      # MyBatis XML 매퍼
│   │   └── 📁 static/scripts/              # 정적 JavaScript 파일
│   └── 📁 webapp/views/                    # JSP 뷰 파일들
└── 📁 test/java/com/farm404/samyang/       # 테스트 코드
```

---

## 🏛️ 아키텍처 구성

### MVC 패턴 구조
```
📱 View Layer (JSP)
    ↕️
🎮 Controller Layer
    ↕️
⚙️ Service Layer
    ↕️
🗃️ Data Access Layer (MyBatis)
    ↕️
🗄️ Database (MySQL)
```

### 패키지 구조
- **`com.farm404.samyang`** - 루트 패키지
  - **`controller`** - 웹 요청 처리
  - **`service`** - 비즈니스 로직
  - **`dto`** - 데이터 전송 객체
  - **`mapper`** - 데이터 접근 계층
  - **`exception`** - 예외 처리

---

## 🎮 컨트롤러 계층

### HomeController.java
**위치**: `src/main/java/com/farm404/samyang/controller/`  
**역할**: 메인 페이지 및 대시보드 기능  
**주요 기능**:
- 📊 대시보드 통계 표시 (`/`)
- ❌ 에러 페이지 처리 (`/error`)

### UserController.java
**위치**: `src/main/java/com/farm404/samyang/controller/`  
**역할**: 사용자 관리 기능  
**주요 기능**:
- 👤 사용자 등록/수정/삭제
- 🔐 로그인/로그아웃 처리

### CropController.java
**위치**: `src/main/java/com/farm404/samyang/controller/`  
**역할**: 작물 관리 기능  
**주요 기능**:
- 🌱 작물 등록/수정/삭제
- 📋 작물 목록 조회

### FarmDiaryController.java
**위치**: `src/main/java/com/farm404/samyang/controller/`  
**역할**: 농사일지 관리 기능  
**주요 기능**:
- 📝 농사일지 작성/수정/삭제
- 📅 일지 목록 조회

---

## 📋 DTO (Data Transfer Objects)

### 핵심 DTO 클래스들
- **UserDTO.java** - 사용자 정보 (userId, name, email, password, phone, address, isAdmin, createdAt)
- **CropDTO.java** - 작물 정보
- **FarmDiaryDTO.java** - 농사일지 정보
- **CommentDTO.java** - 댓글 정보
- **ReviewDTO.java** - 리뷰 정보
- **ReportDTO.java** - 신고 정보
- **PaymentMethodDTO.java** - 결제 방법 정보
- **PageRequest.java** - 페이징 요청 객체

---

## ⚙️ 서비스 계층

### UserService.java
**위치**: `src/main/java/com/farm404/samyang/service/`  
**역할**: 사용자 관련 비즈니스 로직

### CropService.java
**위치**: `src/main/java/com/farm404/samyang/service/`  
**역할**: 작물 관련 비즈니스 로직

### FarmDiaryService.java
**위치**: `src/main/java/com/farm404/samyang/service/`  
**역할**: 농사일지 관련 비즈니스 로직

---

## 🗃️ 데이터 접근 계층 (MyBatis)

### Java 매퍼 인터페이스
- **UserMapper.java** - 사용자 데이터 접근 인터페이스
- **CropMapper.java** - 작물 데이터 접근 인터페이스
- **FarmDiaryMapper.java** - 농사일지 데이터 접근 인터페이스

### XML 매퍼 파일
**위치**: `src/main/resources/mapper/`
- **UserMapper.xml** - 사용자 관련 SQL 쿼리
- **CropMapper.xml** - 작물 관련 SQL 쿼리
- **FarmDiaryMapper.xml** - 농사일지 관련 SQL 쿼리
- **CommentMapper.xml** - 댓글 관련 SQL 쿼리
- **ReviewMapper.xml** - 리뷰 관련 SQL 쿼리
- **ReportMapper.xml** - 신고 관련 SQL 쿼리
- **PaymentMethodMapper.xml** - 결제 방법 관련 SQL 쿼리

---

## 🎯 예외 처리

### ErrorCode.java
**위치**: `src/main/java/com/farm404/samyang/exception/`  
**역할**: 에러 코드 정의

### GlobalExceptionHandler.java
**위치**: `src/main/java/com/farm404/samyang/exception/`  
**역할**: 전역 예외 처리기

### SamyangException.java
**위치**: `src/main/java/com/farm404/samyang/exception/`  
**역할**: 커스텀 예외 클래스

---

## 🖼️ 뷰 계층 (JSP)

### 페이지 구조
**위치**: `src/main/webapp/views/`
```
views/
├── 🏠 home.jsp                 # 메인 대시보드
├── 🔐 login.jsp                # 로그인 페이지
├── ❌ error.jsp                # 에러 페이지
├── 📁 common/
│   ├── 📄 header.jsp           # 공통 헤더
│   └── 📄 footer.jsp           # 공통 푸터
└── 📁 user/
    └── 🔐 login.jsp            # 사용자 로그인 페이지
```

---

## ⚙️ 설정 파일

### application.yml
**위치**: `src/main/resources/`  
**주요 설정**:
- 🗄️ **데이터베이스**: MySQL (34.47.105.223)
- 🔧 **MyBatis**: 매퍼 위치, 설정
- 🖼️ **JSP**: 뷰 prefix/suffix 설정
- 🌍 **프로파일**: 환경별 설정 지원

### application-local.yml
**위치**: `src/main/resources/`  
**역할**: 로컬 개발 환경 전용 설정

---

## 🧪 테스트 구조

### 테스트 클래스들
**위치**: `src/test/java/com/farm404/samyang/`
- **SamyangApplicationTests.java** - 애플리케이션 통합 테스트
- **service/UserServiceTest.java** - 사용자 서비스 테스트
- **service/CropServiceTest.java** - 작물 서비스 테스트
- **service/FarmDiaryServiceTest.java** - 농사일지 서비스 테스트

---

## 🔧 빌드 및 의존성

### 주요 의존성 (build.gradle)
- **Spring Boot 3.5.3** - 웹 프레임워크
- **MyBatis 3.0.5** - ORM 프레임워크
- **MySQL Connector** - 데이터베이스 드라이버
- **Lombok** - 코드 간소화
- **JSP/JSTL** - 뷰 템플릿 엔진

### 빌드 산출물
- **build/** - 컴파일된 클래스 및 리소스
- **bin/** - IDE 컴파일 결과

---

## 🔍 주요 특징

### 아키텍처 패턴
- ✅ **MVC 패턴** - 관심사 분리
- ✅ **계층화 아키텍처** - Controller → Service → Mapper
- ✅ **의존성 주입** - Spring @Autowired 사용

### 데이터베이스 설계
- 🗄️ **테이블**: User, Crop, FarmDiary, Comment, Review, Report, PaymentMethod
- 🔐 **보안**: 패스워드 암호화 지원
- 📊 **페이징**: PageRequest 객체 활용

### 프론트엔드
- 🖼️ **JSP** - 서버사이드 렌더링
- 📜 **jQuery 3.7.1** - JavaScript 라이브러리
- 🎨 **공통 컴포넌트** - header/footer 분리

---

## 🚀 개발 워크플로우

### 1. 새로운 기능 추가 시
1. **DTO 정의** → 2. **Mapper Interface & XML** → 3. **Service 구현** → 4. **Controller 구현** → 5. **JSP 뷰 작성** → 6. **테스트 코드 작성**

### 2. 데이터베이스 연동
1. **MySQL 스키마 설계** → 2. **MyBatis XML 매퍼 작성** → 3. **Java 매퍼 인터페이스 정의** → 4. **DTO 매핑 설정**

### 3. 예외 처리
- **GlobalExceptionHandler** 활용한 중앙집중식 예외 처리
- **SamyangException** 커스텀 예외 사용
- **ErrorCode** 체계적인 에러 분류

---

## 📊 메트릭스

### 코드베이스 크기
- **Java 파일**: 27개
- **XML 매퍼**: 7개
- **JSP 뷰**: 6개
- **설정 파일**: 2개 (yml)

### 패키지별 분포
- **Controller**: 4개 클래스
- **Service**: 3개 클래스
- **DTO**: 8개 클래스
- **Mapper**: 3개 인터페이스
- **Exception**: 3개 클래스

---

## 🔐 보안 고려사항

### 현재 구현된 보안 기능
- ✅ **SQL Injection 방지** - MyBatis 파라미터 바인딩
- ✅ **패스워드 처리** - DTO에서 분리된 관리
- ✅ **에러 처리** - 상세 정보 노출 방지

### 보안 개선 권장사항
- 🚧 **패스워드 암호화** (BCrypt 등)
- 🚧 **세션 관리** 강화
- 🚧 **입력값 검증** 추가
- 🚧 **CSRF 토큰** 적용

---

*이 인덱스는 Claude Code를 통해 생성되었으며, 프로젝트의 현재 상태를 반영합니다.*  
*마지막 업데이트: 2025-07-28*