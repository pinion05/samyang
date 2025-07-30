# Samyang 농업 플랫폼 개발 작업 목록

## 🎯 프로젝트 정보
- **프로젝트명**: Samyang 농업 플랫폼
- **개발 목표**: 농업 관리 시스템 (대학교 과제용)
- **예상 개발 기간**: 12-18일
- **기술 스택**: Spring Boot 3.x, JSP, MySQL 8.x, MyBatis

## 📋 작업 목록

### 1단계: 기본 인프라 구축 (1-2일)

#### 1.1 프로젝트 초기 설정
- [x] Spring Boot 프로젝트 기본 설정
- [x] build.gradle 의존성 설정 (Spring Boot, JSP, MyBatis, MySQL)
- [x] application.yml 설정 (데이터베이스 연결 정보, MyBatis 설정)

#### 1.2 데이터베이스 설정
- [ ] MySQL 데이터베이스 연결 테스트
- [ ] 데이터베이스 테이블 생성 스크립트 작성
- [ ] 7개 테이블 생성 (User, Crop, FarmingDiary, PaymentMethod, Review, Comment, Report)

#### 1.3 기본 패키지 구조 생성
- [x] 패키지 구조 생성 (controller, service, dto, mapper, config, util)
- [ ] 기본 설정 클래스 생성 (WebConfig, DatabaseConfig)

### 2단계: 사용자 관리 구현 (2-3일)

#### 2.1 User 엔티티 개발
- [x] UserDto 클래스 생성
- [x] UserMapper 인터페이스 생성
- [x] UserMapper.xml 작성 (CRUD SQL)
- [x] UserService 클래스 구현
- [x] UserController 클래스 구현

#### 2.2 User 관련 화면 개발
- [x] login.jsp 화면 구현
- [x] register.jsp 화면 구현
- [ ] profile.jsp 화면 구현
- [ ] users.jsp 화면 구현 (관리자용)

#### 2.3 인증 및 세션 관리
- [ ] 로그인/로그아웃 기능 구현
- [ ] 세션 관리 구현
- [ ] 권한 체크 (일반 사용자/관리자)

### 3단계: 농업 핵심 기능 구현 (3-4일)

#### 3.1 Crop (작물) 기능 개발
- [ ] CropDto 클래스 생성
- [ ] CropMapper 인터페이스 및 XML 작성
- [ ] CropService 클래스 구현
- [ ] CropController 클래스 구현
- [ ] crop-list.jsp 화면 구현
- [ ] crop-detail.jsp 화면 구현
- [ ] crop-form.jsp 화면 구현

#### 3.2 FarmingDiary (농업일지) 기능 개발
- [ ] FarmingDiaryDto 클래스 생성
- [ ] FarmingDiaryMapper 인터페이스 및 XML 작성
- [ ] FarmingDiaryService 클래스 구현
- [ ] FarmingDiaryController 클래스 구현
- [ ] diary-list.jsp 화면 구현
- [ ] diary-detail.jsp 화면 구현
- [ ] diary-form.jsp 화면 구현
- [ ] diary-calendar.jsp 화면 구현
- [ ] 사진 업로드 기능 구현

### 4단계: 부가 기능 구현 (2-3일)

#### 4.1 PaymentMethod (결제수단) 기능 개발
- [x] PaymentMethodDto 클래스 생성
- [x] PaymentMethodMapper 인터페이스 및 XML 작성
- [x] PaymentMethodService 클래스 구현
- [x] PaymentMethodController 클래스 구현
- [x] payment-list.jsp 화면 구현
- [x] payment-form.jsp 화면 구현

#### 4.2 Review (리뷰) 기능 개발
- [x] ReviewDto 클래스 생성
- [x] ReviewMapper 인터페이스 및 XML 작성
- [x] ReviewService 클래스 구현
- [x] ReviewController 클래스 구현
- [ ] review-list.jsp 화면 구현
- [ ] review-detail.jsp 화면 구현
- [ ] review-form.jsp 화면 구현
- [ ] 평점 시스템 구현

### 5단계: 커뮤니티 기능 구현 (2일)

#### 5.1 Comment (댓글) 기능 개발
- [x] CommentDto 클래스 생성
- [x] CommentMapper 인터페이스 및 XML 작성
- [x] CommentService 클래스 구현
- [x] CommentController 클래스 구현
- [x] comment-list.jsp 화면 구현
- [x] comment-form.jsp 화면 구현

#### 5.2 Report (신고) 기능 개발
- [x] ReportDto 클래스 생성
- [x] ReportMapper 인터페이스 및 XML 작성
- [x] ReportService 클래스 구현
- [x] ReportController 클래스 구현
- [x] report-list.jsp 화면 구현 (관리자용)
- [x] report-detail.jsp 화면 구현
- [x] report-form.jsp 화면 구현

### 6단계: UI/UX 개선 및 테스트 (2-3일)

#### 6.1 공통 컴포넌트 개발
- [ ] header.jsp 구현 (네비게이션 메뉴)
- [ ] footer.jsp 구현
- [ ] sidebar.jsp 구현 (사이드바 메뉴)
- [ ] index.jsp 구현 (메인 대시보드)

#### 6.2 스타일 및 스크립트
- [ ] style.css 작성 (전체 스타일)
- [ ] main.js 작성 (공통 JavaScript)
- [ ] 반응형 웹 디자인 적용
- [ ] jQuery 활용한 동적 기능 구현

#### 6.3 테스트 및 검증
- [ ] 각 엔티티별 CRUD 기능 테스트
- [ ] 사용자 권한별 기능 테스트
- [ ] 화면 간 네비게이션 테스트
- [ ] 데이터 입력 검증 테스트
- [ ] 에러 처리 테스트

### 7단계: 최종 점검 및 배포 준비

#### 7.1 통합 테스트
- [ ] 전체 시나리오 테스트
- [ ] 성능 테스트
- [ ] 브라우저 호환성 테스트

#### 7.2 문서화
- [ ] README.md 작성
- [ ] 설치 및 실행 가이드 작성
- [ ] API 명세서 정리

## 📌 주요 체크포인트

### 필수 구현 사항
- ✅ 7개 테이블 모두 구현
- ✅ 각 테이블별 완전한 CRUD 기능
- ✅ JSP를 활용한 웹 화면 구현
- ✅ MyBatis를 활용한 데이터 액세스

### 개발 원칙
- 🎯 기능의 정상 동작 최우선
- 🎯 단계별 테스트 수행
- 🎯 User 엔티티 우선 개발
- 🎯 기본적인 입력값 검증 구현
- 가장 간단한 아키텍쳐
- 테스트 작성금지

## 🚀 우선순위 정리

1. **최우선**: User 관리 기능 (모든 기능의 기반)
2. **높음**: Crop, FarmingDiary (핵심 농업 기능)
3. **중간**: PaymentMethod, Review (부가 기능)
4. **낮음**: Comment, Report (커뮤니티 기능)

## 📅 일정 요약
- **1-2일차**: 프로젝트 설정 및 기본 구조
- **3-5일차**: User 관리 완성
- **6-9일차**: 농업 핵심 기능 완성
- **10-12일차**: 부가 기능 완성
- **13-14일차**: 커뮤니티 기능 완성
- **15-18일차**: UI/UX 개선 및 최종 테스트