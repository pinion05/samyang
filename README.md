# Samyang - 농업 플랫폼

## 📋 프로젝트 개요

Samyang은 농업인들을 위한 통합 플랫폼으로, 작물 관리, 농사일지 작성, 사용자 관리 기능을 제공합니다.

### 🛠 기술 스택
- **Backend**: Spring Boot 3.5.3, MyBatis
- **Frontend**: JSP, Bootstrap 5
- **Database**: MySQL 8.0
- **Build Tool**: Gradle
- **Testing**: JUnit 5, Mockito
- **Logging**: SLF4J + Logback

### 🏗 아키텍처
- **MVC 패턴**: Controller → Service → Mapper → Database
- **예외 처리**: 통합 예외 처리 시스템 (SamyangException, GlobalExceptionHandler)
- **로깅**: 구조화된 로깅 시스템
- **트랜잭션**: 선언적 트랜잭션 관리

## 🚀 주요 기능

### 👤 사용자 관리
- 사용자 등록/로그인/수정/삭제
- 이메일 중복 검증
- 관리자 권한 관리

### 🌱 작물 관리
- 작물 등록 및 상태 관리
- 파종부터 수확까지 전 과정 추적
- 사용자별 작물 목록 관리

### 📝 농사일지
- 일일 농사 활동 기록
- 작물별 일지 관리
- 사진 첨부 기능

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/farm404/samyang/
│   │   ├── controller/          # 웹 컨트롤러
│   │   ├── service/             # 비즈니스 로직
│   │   ├── mapper/              # MyBatis 매퍼
│   │   ├── dto/                 # 데이터 전송 객체
│   │   ├── exception/           # 예외 처리
│   │   └── SamyangApplication.java
│   ├── resources/
│   │   ├── mapper/              # MyBatis XML 매퍼
│   │   ├── application.yml      # 설정 파일
│   │   └── application-local.yml
│   └── webapp/WEB-INF/views/    # JSP 뷰
└── test/                        # 단위 테스트
```

## 🔧 환경 설정

### 필수 요구사항
- Java 17+
- MySQL 8.0+
- Gradle 7.0+

### 환경 변수
```bash
# 데이터베이스 설정
export DATABASE_URL="jdbc:mysql://localhost:3306/samyang?charsetEncoding=utf-8&serverTimezone=Asia/Seoul"
export DATABASE_USERNAME="samyang_user"
export DATABASE_PASSWORD="your_secure_password"
```

### 애플리케이션 실행
```bash
# 개발 환경
./gradlew bootRun

# 프로덕션 환경
./gradlew build
java -jar build/libs/samyang-*.jar
```

## 🧪 테스트

### 단위 테스트 실행
```bash
./gradlew test
```

### 테스트 커버리지
- UserService: 100% 메서드 커버리지
- CropService: 100% 메서드 커버리지  
- FarmDiaryService: 100% 메서드 커버리지

## 🔐 보안 특징

### 구현된 보안 기능
- ✅ 환경 변수를 통한 데이터베이스 인증 정보 보호
- ✅ 구조화된 예외 처리로 정보 노출 방지
- ✅ SLF4J를 통한 안전한 로깅
- ✅ 입력 데이터 검증

### 보안 권장사항
- 비밀번호 해싱 (BCrypt 등) 구현 권장
- HTTPS 연결 설정 권장
- JWT 토큰 기반 인증 구현 권장

## 📊 성능 최적화

### 구현된 최적화
- ✅ MyBatis 2차 캐시 설정
- ✅ 트랜잭션 최적화 (@Transactional)
- ✅ 읽기 전용 트랜잭션 분리
- ✅ 페이징 지원 (PageRequest DTO)

### 권장 개선사항
- 연결 풀 최적화
- 쿼리 성능 모니터링
- 인덱스 최적화

## 🐛 문제 해결

### 일반적인 문제
1. **데이터베이스 연결 실패**
   - 환경 변수 확인
   - MySQL 서비스 상태 확인

2. **한글 인코딩 문제**
   - `application.yml`의 characterEncoding 설정 확인
   - 데이터베이스 charset 설정 확인

## 📝 API 문서

### 사용자 API
- `GET /user/list` - 사용자 목록 조회
- `POST /user/register` - 사용자 등록
- `PUT /user/update` - 사용자 정보 수정
- `DELETE /user/delete/{id}` - 사용자 삭제

### 작물 API  
- `GET /crop/list` - 작물 목록 조회
- `POST /crop/register` - 작물 등록
- `PUT /crop/update` - 작물 정보 수정
- `DELETE /crop/delete/{id}` - 작물 삭제

### 농사일지 API
- `GET /farm-diary/list` - 농사일지 목록 조회
- `POST /farm-diary/register` - 농사일지 등록
- `PUT /farm-diary/update` - 농사일지 수정
- `DELETE /farm-diary/delete/{id}` - 농사일지 삭제

## 📈 개발 로드맵

### v1.1 (예정)
- [ ] JWT 인증 시스템
- [ ] 파일 업로드 기능
- [ ] 알림 시스템

### v1.2 (예정)
- [ ] REST API 전환
- [ ] React 프론트엔드
- [ ] 실시간 데이터 동기화

## 🤝 기여 방법

1. Fork 프로젝트
2. Feature 브랜치 생성 (`git checkout -b feature/amazing-feature`)
3. 변경사항 커밋 (`git commit -m 'Add amazing feature'`)
4. 브랜치에 Push (`git push origin feature/amazing-feature`)
5. Pull Request 생성

## 📄 라이센스

이 프로젝트는 MIT 라이센스를 따릅니다.

---

# 데이터베이스 스키마 상세 설계

```sql
 CREATE TABLE 사용자 (
      사용자ID INT AUTO_INCREMENT PRIMARY KEY,
      이름 VARCHAR(255) NOT NULL,
      이메일 VARCHAR(255) NOT NULL UNIQUE,
      비밀번호 VARCHAR(255) NOT NULL,
      전화번호 VARCHAR(20),
      주소 VARCHAR(255),
      관리자여부 BOOLEAN DEFAULT FALSE,
      가입일시 DATETIME DEFAULT CURRENT_TIMESTAMP
  );

  -- 결제수단 테이블
  CREATE TABLE 결제수단 (
      결제수단ID INT AUTO_INCREMENT PRIMARY KEY,
      사용자ID INT NOT NULL,
      카드번호 VARCHAR(255) NOT NULL,
      만료일 DATE NOT NULL,
      CVC VARCHAR(4) NOT NULL,
      FOREIGN KEY (사용자ID) REFERENCES 사용자(사용자ID)
  );

  -- 작물 테이블
  CREATE TABLE 작물 (
      작물ID INT AUTO_INCREMENT PRIMARY KEY,
      사용자ID INT NOT NULL,
      작물명 VARCHAR(255) NOT NULL,
      품종 VARCHAR(255),
      심은날짜 DATE,
      예상수확일 DATE,
      상태 VARCHAR(50),
      FOREIGN KEY (사용자ID) REFERENCES 사용자(사용자ID)
  );

  -- 농사일지 테이블
  CREATE TABLE 농사일지 (
      농사일지ID INT AUTO_INCREMENT PRIMARY KEY,
      사용자ID INT NOT NULL,
      날짜 DATE NOT NULL,
      활동_유형 VARCHAR(100),
      내용 TEXT NOT NULL,
      사진_URL VARCHAR(255),
      작성일시 DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (사용자ID) REFERENCES 사용자(사용자ID)
  );

  -- 댓글 테이블 (게시물 테이블이 없어서 게시물ID를 INT로만 설정)
  CREATE TABLE 댓글 (
      댓글ID INT AUTO_INCREMENT PRIMARY KEY,
      게시물ID INT NOT NULL,
      사용자ID INT NOT NULL,
      내용 TEXT NOT NULL,
      작성일시 DATETIME DEFAULT CURRENT_TIMESTAMP,
      수정일자 DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      FOREIGN KEY (사용자ID) REFERENCES 사용자(사용자ID)
  );

  -- 리뷰 테이블 (주문상품 테이블이 없어서 주문상품ID를 INT로만 설정)
  CREATE TABLE 리뷰 (
      리뷰ID INT AUTO_INCREMENT PRIMARY KEY,
      주문상품ID INT NOT NULL,
      사용자ID INT NOT NULL,
      평점 INT NOT NULL CHECK (평점 >= 1 AND 평점 <= 5),
      내용 TEXT,
      작성일시 DATETIME DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (사용자ID) REFERENCES 사용자(사용자ID)
  );

  -- 신고 테이블
  CREATE TABLE 신고 (
      신고ID INT AUTO_INCREMENT PRIMARY KEY,
      신고자_사용자ID INT NOT NULL,
      신고_유형 VARCHAR(50) NOT NULL,
      신고_대상_ID INT NOT NULL,
      사유 TEXT NOT NULL,
      신고일시 DATETIME DEFAULT CURRENT_TIMESTAMP,
      처리상태 VARCHAR(50) DEFAULT '접수',
      FOREIGN KEY (신고자_사용자ID) REFERENCES 사용자(사용자ID)
  );
```














## 데이터베이스 ERD

### 사용자 관리

```sql
Table 사용자 {
  사용자ID int [pk, increment]
  이름 varchar(255) [not null]
  이메일 varchar(255) [not null, unique]
  비밀번호 varchar(255) [not null]
  전화번호 varchar(20)
  주소 varchar(255)
  관리자여부 boolean [default: false] // 관리자 여부
  가입일시 datetime [default: 'now()']
}


Table 결제수단 {
  결제수단ID int [pk, increment]
  사용자ID int [not null]
  카드번호 varchar(255) [not null] // 암호화 저장
  만료일 date [not null]
  CVC varchar(4) [not null] // 암호화 저장
}
```

### 농업 관리

```sql
Table 작물 {
  작물ID int [pk, increment]
  사용자ID int [not null]
  작물명 varchar(255) [not null]
  품종 varchar(255)
  심은날짜 date
  예상수확일 date
  상태 varchar(50) // 예: '파종', '성장 중', '수확 완료'
}

Table 농사일지 {
  농사일지ID int [pk, increment]
  사용자ID int [not null]
  날짜 date [not null]
  활동_유형 varchar(100) // 예: '물주기', '비료주기', '수확'
  내용 text
  사진_URL varchar(255)
}
```

### 상품 및 주문 관리

```sql
Table 상품 {
  상품ID int [pk, increment]
  관리자ID int [not null] // 누가 이 상품을 관리하는지
  상품명 varchar(255) [not null]
  설명 text
  가격 decimal(10, 2) [not null]
  재고_수량 int [not null]
  카테고리 varchar(100) // 예: '농산물', '농기구'
  이미지_URL varchar(255)
}

Table 주문 {
  주문ID int [pk, increment]
  사용자ID int [not null]
  상품ID int 
  주문갯수 int
  주문일시 datetime [default: 'now()']
  상태 varchar(50) // 예: '주문 완료', '배송 중', '배송 완료', '취소'
}

### 커뮤니티 관리

```sql
Table 공지사항 {
  공지사항ID int [pk, increment]
  공지사항_이름 varchar(100) [not null, unique] // 예: '공지사항', '자유게시판'
  설명 varchar()
  사진_URL varchar(255)
}

Table 게시물 {
  게시물ID int [pk, increment]
  사용자ID int [not null]
  제목 varchar(255) [not null]
  내용 TEXT [not null]
  사진_URL varchar(255)
  작성일시 datetime [default: 'now()']
}

Table 댓글 {
  댓글ID int [pk, increment]
  게시물ID int [not null]
  사용자ID int [not null]
  내용 text [not null]
  작성일시 datetime [default: 'now()']
  수정일자 datetime [default: 'now()']
}

Table 리뷰 {
  리뷰ID int [pk, increment]
  주문상품ID int [not null]
  사용자ID int [not null]
  평점 int [not null] // 1~5
  내용 text
  작성일시 datetime [default: 'now()']
}

Table 신고 {
  신고ID int [pk, increment]
  신고자_사용자ID int [not null]
  신고_유형 varchar(50) [not null] // '게시물', '댓글', '리뷰'
  신고_대상_ID int [not null]
  사유 text [not null]
  신고일시 datetime [default: 'now()']
  처리상태 varchar(50) [default: '접수'] // '접수', '처리 중', '처리 완료', '반려'
}
```

## 테이블 관계 (Foreign Key)

### 사용자 중심 관계
```sql
Ref: 결제수단.사용자ID > 사용자.사용자ID
Ref: 작물.사용자ID > 사용자.사용자ID
Ref: 농사일지.사용자ID > 사용자.사용자ID
Ref: 주문.사용자ID > 사용자.사용자ID
Ref: 게시물.사용자ID > 사용자.사용자ID
Ref: 댓글.사용자ID > 사용자.사용자ID
Ref: 리뷰.사용자ID > 사용자.사용자ID
Ref: 신고.신고자_사용자ID > 사용자.사용자ID
```

### 농업 관리 관계
```sql
Ref: 농사일지.작물ID > 작물.작물ID
```

### 상품 및 주문 관계
```sql
Ref: 상품.관리자ID > 관리자.관리자ID
Ref: 주문상품.주문ID > 주문.주문ID
Ref: 주문상품.상품ID > 상품.상품ID
```

### 커뮤니티 관계
```sql
Ref: 댓글.게시물ID > 게시물.게시물ID
Ref: 리뷰.주문상품ID > 주문상품.주문상품ID
```

### 동적 참조 관계
```sql
// 신고 대상이 되는 테이블과의 관계는 유형별로 동적으로 참조됩니다.
// 신고.신고_대상_ID는 신고_유형에 따라 다음을 참조:
// - '게시물' 유형: 게시물.게시물ID
// - '댓글' 유형: 댓글.댓글ID  
// - '리뷰' 유형: 리뷰.리뷰ID
```

## 주요 특징

### 보안
- 카드번호, CVC는 암호화하여 저장
- 비밀번호는 해시화하여 저장
- 개인정보 보호를 위한 데이터 마스킹 적용

### 확장성
- 관리자 권한 레벨로 세분화된 관리 가능
- 다양한 카테고리의 상품 지원
- 농사일지의 다양한 활동 유형 지원

### 사용자 경험
- 주문 상태 실시간 추적
- 리뷰 시스템으로 상품 품질 관리
- 신고 시스템으로 커뮤니티 건전성 유지
## 개발 환경 설정

### 핫로드(Hot Reload) 사용법

1. **의존성**  
   - build.gradle에 spring-boot-devtools 추가

2. **설정**  
   - src/main/resources/application.yml에 devtools/restart, livereload 활성화  
   - 정적 리소스 캐시 비활성화(web/resources/cache/period: 0)

3. **실행**  
   ```bash
   ./gradlew bootRun
   ```

4. **사용**  
   - Java 변경 시 애플리케이션 자동 재시작  
   - JSP, CSS, JS 변경 시 브라우저 자동 새로고침(LiveReload)

## 데이터베이스 초기화

```sql
-- 데이터베이스 생성
CREATE DATABASE samyang_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 사용자 생성 및 권한 부여
CREATE USER 'samyang_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON samyang_db.* TO 'samyang_user'@'localhost';
FLUSH PRIVILEGES;
```
