# Samyang - 농업 플랫폼 데이터베이스 스키마

## 데이터베이스 ERD

### 사용자 및 인증 관리

```sql
Table 사용자 {
  사용자ID bigint [pk, increment]
  이메일 varchar(255) [not null, unique]
  비밀번호_해시 varchar(255) [not null] // bcrypt 해시
  이름 varchar(100) [not null]
  전화번호 varchar(20)
  성별 varchar(10) // 'M', 'F', 'OTHER'
  생년월일 date
  역할 enum('USER', 'FARMER', 'ADMIN', 'SUPER_ADMIN') [default: 'USER']
  상태 enum('ACTIVE', 'INACTIVE', 'SUSPENDED', 'DELETED') [default: 'ACTIVE']
  이메일_인증여부 boolean [default: false]
  마케팅_동의여부 boolean [default: false]
  가입일시 datetime [default: 'now()']
  최종_로그인일시 datetime
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  삭제일시 datetime
}

Table 주소 {
  주소ID bigint [pk, increment]
  사용자ID bigint [not null]
  주소_유형 enum('HOME', 'FARM', 'SHIPPING') [not null]
  수신자명 varchar(100) [not null]
  전화번호 varchar(20) [not null]
  우편번호 varchar(10) [not null]
  시도 varchar(50) [not null]
  시군구 varchar(50) [not null]
  읍면동 varchar(50) [not null]
  상세주소 varchar(255) [not null]
  배송_메모 varchar(255)
  기본_주소여부 boolean [default: false]
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 결제수단 {
  결제수단ID bigint [pk, increment]
  사용자ID bigint [not null]
  카드_토큰 varchar(255) [not null] // PG사 토큰 (PCI DSS 준수)
  카드_유형 enum('CREDIT', 'DEBIT', 'PREPAID') [not null]
  카드_브랜드 varchar(50) [not null] // 'VISA', 'MASTERCARD', 'AMEX' 등
  카드번호_마스킹 varchar(20) [not null] // **** **** **** 1234
  만료년월 varchar(7) [not null] // YYYY-MM
  카드소유자명 varchar(100) [not null]
  기본_결제수단여부 boolean [default: false]
  상태 enum('ACTIVE', 'EXPIRED', 'BLOCKED') [default: 'ACTIVE']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 로그인_이력 {
  이력ID bigint [pk, increment]
  사용자ID bigint [not null]
  로그인_방식 enum('EMAIL', 'SOCIAL', 'SMS') [not null]
  IP_주소 varchar(45) [not null]
  사용자_에이전트 text
  로그인_성공여부 boolean [not null]
  로그인일시 datetime [default: 'now()']
}
```

### 농업 관리

```sql
Table 농장 {
  농장ID bigint [pk, increment]
  농장주_사용자ID bigint [not null]
  농장명 varchar(100) [not null]
  농장_유형 enum('INDIVIDUAL', 'CORPORATE', 'COOPERATIVE') [not null]
  사업자_등록번호 varchar(20)
  총_면적 decimal(10, 2) // 단위: 평방미터
  주소ID bigint [not null]
  위도 decimal(10, 8)
  경도 decimal(11, 8)
  토양_타입 varchar(50)
  기후_조건 varchar(100)
  인증_정보 json // 유기농, GAP 등 인증 정보
  상태 enum('ACTIVE', 'INACTIVE', 'SUSPENDED') [default: 'ACTIVE']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  삭제일시 datetime
}

Table 필드 {
  필드ID bigint [pk, increment]
  농장ID bigint [not null]
  필드명 varchar(100) [not null]
  면적 decimal(10, 2) [not null] // 단위: 평방미터
  토양_pH decimal(3, 1)
  배수_상태 enum('EXCELLENT', 'GOOD', 'FAIR', 'POOR')
  일조량_레벨 enum('HIGH', 'MEDIUM', 'LOW')
  관수_시설여부 boolean [default: false]
  온실_여부 boolean [default: false]
  상태 enum('ACTIVE', 'FALLOW', 'MAINTENANCE') [default: 'ACTIVE']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 작물_마스터 {
  작물_마스터ID bigint [pk, increment]
  작물명 varchar(100) [not null, unique]
  학명 varchar(100)
  작물_분류 enum('GRAIN', 'VEGETABLE', 'FRUIT', 'HERB', 'FLOWER', 'OTHER') [not null]
  생장_기간_평균일 int // 파종부터 수확까지 평균 일수
  적정_온도_최소 decimal(4, 1)
  적정_온도_최대 decimal(4, 1)
  적정_pH_최소 decimal(3, 1)
  적정_pH_최대 decimal(3, 1)
  파종_시기 varchar(100) // 예: '3월-5월'
  수확_시기 varchar(100) // 예: '7월-9월'
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 작물_재배 {
  재배ID bigint [pk, increment]
  필드ID bigint [not null]
  작물_마스터ID bigint [not null]
  품종명 varchar(100)
  파종일 date [not null]
  예상_수확일 date
  실제_수확일 date
  재배_면적 decimal(10, 2) [not null] // 단위: 평방미터 
  식물_수량 int // 심은 개체 수
  재배_방식 enum('OUTDOOR', 'GREENHOUSE', 'HYDROPONIC', 'VERTICAL') [not null]
  유기농_여부 boolean [default: false]
  상태 enum('PLANNED', 'SEEDED', 'GROWING', 'HARVESTING', 'COMPLETED', 'FAILED') [default: 'PLANNED']
  예상_수확량 decimal(10, 2) // 단위: kg
  실제_수확량 decimal(10, 2) // 단위: kg
  품질_등급 enum('PREMIUM', 'GRADE_A', 'GRADE_B', 'GRADE_C') 
  총_투입비용 decimal(12, 2)
  총_수익 decimal(12, 2)
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  완료일시 datetime
}

Table 농업_활동 {
  활동ID bigint [pk, increment]
  재배ID bigint [not null]
  담당자_사용자ID bigint [not null]
  활동_유형 enum('SEEDING', 'WATERING', 'FERTILIZING', 'PEST_CONTROL', 'PRUNING', 'HARVESTING', 'OTHER') [not null]
  활동_상세 varchar(255) [not null]
  사용_자재 json // [{"name": "비료A", "quantity": 10, "unit": "kg"}]
  작업_시간 decimal(4, 2) // 단위: 시간
  작업_인원 int [default: 1]
  비용 decimal(10, 2)
  날씨_상태 varchar(50)
  토양_습도 decimal(5, 2) // 단위: %
  기온 decimal(4, 1) // 단위: 섭씨
  습도 decimal(5, 2) // 단위: %
  메모 text
  사진_URL json // 여러 사진 URL 배열
  활동일시 datetime [not null]
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 농업_자재 {
  자재ID bigint [pk, increment]
  농장ID bigint [not null]
  자재_카테고리 enum('SEED', 'FERTILIZER', 'PESTICIDE', 'TOOL', 'EQUIPMENT', 'OTHER') [not null]
  자재명 varchar(100) [not null]
  브랜드 varchar(50)
  규격 varchar(50)
  단위 varchar(20) [not null] // kg, L, 개 등
  현재_재고량 decimal(10, 2) [not null]
  최소_재고량 decimal(10, 2) [default: 0]
  단가 decimal(10, 2)
  공급업체 varchar(100)
  유통기한 date
  보관_위치 varchar(100)
  상태 enum('ACTIVE', 'DISCONTINUED', 'OUT_OF_STOCK') [default: 'ACTIVE']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 자재_사용_이력 {
  사용_이력ID bigint [pk, increment]
  농업_활동ID bigint [not null]
  자재ID bigint [not null]
  사용량 decimal(10, 2) [not null]
  단가 decimal(10, 2)
  총_비용 decimal(10, 2)
  사용일시 datetime [not null]
  생성일시 datetime [default: 'now()']
}
```

### 상품 및 주문 관리

```sql
Table 상품_카테고리 {
  카테고리ID bigint [pk, increment]
  상위_카테고리ID bigint // 계층구조 지원
  카테고리명 varchar(100) [not null]
  카테고리_경로 varchar(500) // '/농산물/채소/엽채류'
  정렬_순서 int [default: 0]
  사용여부 boolean [default: true]
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 상품 {
  상품ID bigint [pk, increment]
  판매자_사용자ID bigint [not null]
  카테고리ID bigint [not null]
  상품명 varchar(200) [not null]
  상품_설명 text
  원산지 varchar(100)
  브랜드 varchar(100)
  제조사 varchar(100)
  상품_코드 varchar(50) [unique]
  바코드 varchar(50)
  기본_가격 decimal(12, 2) [not null]
  할인_가격 decimal(12, 2)
  할인_시작일 datetime
  할인_종료일 datetime
  최소_주문수량 int [default: 1]
  최대_주문수량 int
  중량 decimal(8, 2) // 단위: g
  용량 decimal(8, 2) // 단위: ml
  크기_정보 varchar(100) // '10cm x 5cm x 3cm'
  포장_타입 enum('BOX', 'BAG', 'BOTTLE', 'CAN', 'BULK') 
  유통기한_일수 int // 제조일로부터 유통기한까지 일수
  보관_방법 varchar(200)
  주의사항 text
  영양정보 json
  인증_정보 json // 유기농, HACCP 등
  메인_이미지_URL varchar(500)
  상품_이미지_URLs json // 추가 이미지들
  상세_이미지_URLs json // 상세페이지 이미지들
  판매_상태 enum('ON_SALE', 'SOLD_OUT', 'DISCONTINUED', 'PREPARING') [default: 'PREPARING']
  추천여부 boolean [default: false]
  신상품여부 boolean [default: false]
  베스트셀러여부 boolean [default: false]
  조회수 bigint [default: 0]
  좋아요수 bigint [default: 0]
  판매수량 bigint [default: 0]
  SEO_제목 varchar(200)  
  SEO_설명 varchar(500)
  SEO_키워드 varchar(500)
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  삭제일시 datetime
}

Table 상품_재고 {
  재고ID bigint [pk, increment]
  상품ID bigint [not null, unique]
  현재_재고량 int [not null, default: 0]
  안전_재고량 int [default: 10]
  입고_예정량 int [default: 0]
  출고_예정량 int [default: 0]
  마지막_입고일 datetime
  마지막_출고일 datetime
  재고_상태 enum('SUFFICIENT', 'LOW', 'OUT_OF_STOCK', 'OVERSTOCKED') [default: 'SUFFICIENT']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 재고_이력 {
  이력ID bigint [pk, increment]
  상품ID bigint [not null]
  변동_유형 enum('IN', 'OUT', 'ADJUSTMENT', 'LOSS', 'RETURN') [not null]
  변동_사유 varchar(100) [not null] // '입고', '판매', '재고조정', '파손', '반품'
  변동_수량 int [not null]
  변동_전_재고량 int [not null]
  변동_후_재고량 int [not null]
  단가 decimal(10, 2)
  관련_주문ID bigint // 판매/반품의 경우
  담당자_사용자ID bigint [not null]
  메모 varchar(500)
  변동일시 datetime [not null]
  생성일시 datetime [default: 'now()']
}

Table 쿠폰 {
  쿠폰ID bigint [pk, increment]
  쿠폰명 varchar(100) [not null]
  쿠폰_코드 varchar(50) [not null, unique]
  쿠폰_유형 enum('FIXED_AMOUNT', 'PERCENTAGE', 'FREE_SHIPPING') [not null]
  할인_금액 decimal(10, 2) // 고정 금액 할인
  할인_비율 decimal(5, 2) // 퍼센트 할인 (0.00 ~ 100.00)
  최소_주문금액 decimal(10, 2)
  최대_할인금액 decimal(10, 2)
  사용_가능_시작일 datetime [not null]
  사용_가능_종료일 datetime [not null]
  발행_수량 int [not null]
  사용된_수량 int [default: 0]
  1인당_사용제한 int [default: 1]
  적용_대상 enum('ALL', 'CATEGORY', 'PRODUCT', 'USER_GROUP') [not null]
  적용_대상_IDs json // 카테고리ID, 상품ID, 사용자그룹ID 배열
  상태 enum('ACTIVE', 'INACTIVE', 'EXPIRED') [default: 'ACTIVE']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 장바구니 {
  장바구니ID bigint [pk, increment]
  사용자ID bigint [not null]
  상품ID bigint [not null]
  수량 int [not null]
  추가일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 주문 {
  주문ID bigint [pk, increment]
  주문번호 varchar(50) [not null, unique] // 'ORD-20241201-001'
  주문자_사용자ID bigint [not null]
  주문_상태 enum('PENDING', 'CONFIRMED', 'PREPARING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'RETURNED') [default: 'PENDING']
  결제_상태 enum('PENDING', 'PAID', 'FAILED', 'CANCELLED', 'REFUNDED') [default: 'PENDING']
  상품_총액 decimal(12, 2) [not null]
  쿠폰_할인액 decimal(12, 2) [default: 0]
  배송비 decimal(8, 2) [default: 0]
  총_결제금액 decimal(12, 2) [not null]
  배송_주소ID bigint [not null]
  배송_요청사항 varchar(500)
  주문_메모 text
  주문일시 datetime [default: 'now()']
  결제일시 datetime
  배송_시작일시 datetime
  배송_완료일시 datetime
  취소일시 datetime
  취소_사유 varchar(500)
  환불일시 datetime
  환불_사유 varchar(500)
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 주문상품 {
  주문상품ID bigint [pk, increment]
  주문ID bigint [not null]
  상품ID bigint [not null]
  상품명 varchar(200) [not null] // 주문 시점 상품명 보존
  상품_옵션 json // 색상, 사이즈 등
  주문_수량 int [not null]
  상품_단가 decimal(10, 2) [not null] // 주문 시점 가격 보존
  총_상품금액 decimal(12, 2) [not null]
  상품_상태 enum('ORDERED', 'CONFIRMED', 'PREPARING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'RETURNED') [default: 'ORDERED']
  배송_추적번호 varchar(100)
  배송업체 varchar(100)
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 결제 {
  결제ID bigint [pk, increment]
  주문ID bigint [not null]
  결제수단ID bigint
  결제_방법 enum('CARD', 'BANK_TRANSFER', 'VIRTUAL_ACCOUNT', 'MOBILE', 'KAKAO_PAY', 'NAVER_PAY') [not null]
  결제_금액 decimal(12, 2) [not null]
  PG사 varchar(50) [not null] // 'KG이니시스', '나이스페이' 등
  거래ID varchar(100) [not null] // PG사 거래ID
  승인번호 varchar(100)
  결제_상태 enum('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED', 'REFUNDED') [not null]
  결제일시 datetime [not null]
  취소일시 datetime
  환불일시 datetime
  환불_금액 decimal(12, 2)
  실패_사유 varchar(500)
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 배송 {
  배송ID bigint [pk, increment]
  주문ID bigint [not null]
  배송업체 varchar(100) [not null]
  배송_추적번호 varchar(100) [not null, unique]
  발송지_주소 varchar(500) [not null]
  배송지_주소 varchar(500) [not null]
  수신자명 varchar(100) [not null]
  수신자_전화번호 varchar(20) [not null]
  배송_상태 enum('PREPARING', 'SHIPPED', 'IN_TRANSIT', 'OUT_FOR_DELIVERY', 'DELIVERED', 'FAILED') [default: 'PREPARING']
  배송_시작일시 datetime
  배송_완료일시 datetime
  배송_메모 varchar(500)
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}
```

### 커뮤니티 관리

```sql
Table 게시판_카테고리 {
  카테고리ID bigint [pk, increment]
  상위_카테고리ID bigint // 계층구조 지원
  카테고리명 varchar(100) [not null]
  카테고리_설명 text
  카테고리_타입 enum('NOTICE', 'FREE', 'QNA', 'REVIEW', 'FARM_TIPS', 'MARKET_INFO') [not null]
  아이콘_URL varchar(255)
  정렬_순서 int [default: 0]
  글쓰기_권한 enum('ALL', 'FARMER', 'ADMIN') [default: 'ALL']
  댓글_허용여부 boolean [default: true]
  첨부파일_허용여부 boolean [default: true]
  사용여부 boolean [default: true]
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 게시물 {
  게시물ID bigint [pk, increment]
  카테고리ID bigint [not null]
  작성자_사용자ID bigint [not null]
  제목 varchar(255) [not null]
  내용 text [not null]
  요약 varchar(500) // SEO용 요약
  태그 json // ['농업', '유기농', '팁'] 형태의 태그 배열
  공지여부 boolean [default: false]
  중요도 enum('NORMAL', 'IMPORTANT', 'URGENT') [default: 'NORMAL']
  상태 enum('DRAFT', 'PUBLISHED', 'HIDDEN', 'DELETED') [default: 'PUBLISHED']
  게시_시작일시 datetime [default: 'now()']
  게시_종료일시 datetime
  조회수 bigint [default: 0]
  좋아요수 bigint [default: 0]
  댓글수 int [default: 0]
  첨부파일_URLs json // 첨부파일 URL 배열
  썸네일_URL varchar(255)
  SEO_제목 varchar(200)
  SEO_설명 varchar(500)
  검색_키워드 varchar(500)
  IP_주소 varchar(45) [not null]
  사용자_에이전트 varchar(500)
  수정_횟수 int [default: 0]
  최종_수정자_사용자ID bigint
  작성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  삭제일시 datetime
}

Table 댓글 {
  댓글ID bigint [pk, increment]
  게시물ID bigint [not null]
  상위_댓글ID bigint // 대댓글 지원
  작성자_사용자ID bigint [not null]
  내용 text [not null]
  댓글_깊이 int [default: 0] // 0: 원댓글, 1: 대댓글, 2: 대대댓글
  상태 enum('PUBLISHED', 'HIDDEN', 'DELETED', 'REPORTED') [default: 'PUBLISHED']
  좋아요수 int [default: 0]
  신고수 int [default: 0]
  IP_주소 varchar(45) [not null]
  사용자_에이전트 varchar(500)
  수정_횟수 int [default: 0]
  작성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  삭제일시 datetime
}

Table 좋아요 {
  좋아요ID bigint [pk, increment]
  사용자ID bigint [not null]
  대상_유형 enum('POST', 'COMMENT', 'REVIEW') [not null]
  대상_ID bigint [not null] // 게시물ID, 댓글ID, 리뷰ID
  생성일시 datetime [default: 'now()']
}

Table 리뷰 {
  리뷰ID bigint [pk, increment]
  주문상품ID bigint [not null]
  작성자_사용자ID bigint [not null]
  평점 decimal(2, 1) [not null] // 1.0~5.0 (0.5 단위)
  제목 varchar(200)
  내용 text
  장점 text
  단점 text
  추천여부 boolean
  구매_검증여부 boolean [default: false] // 실제 구매 검증
  리뷰_이미지_URLs json // 리뷰 사진들
  리뷰_동영상_URL varchar(255)
  상태 enum('PUBLISHED', 'PENDING', 'REJECTED', 'DELETED') [default: 'PENDING']
  도움됨_수 int [default: 0]
  신고수 int [default: 0]
  관리자_검토여부 boolean [default: false]
  검토_관리자_사용자ID bigint
  검토일시 datetime
  검토_메모 varchar(500)
  작성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
  삭제일시 datetime
}

Table 리뷰_도움됨 {
  도움됨ID bigint [pk, increment]
  리뷰ID bigint [not null]
  사용자ID bigint [not null]
  도움됨_여부 boolean [not null] // true: 도움됨, false: 도움안됨
  생성일시 datetime [default: 'now()']
}

Table 신고 {
  신고ID bigint [pk, increment]
  신고자_사용자ID bigint [not null]
  신고_유형 enum('POST', 'COMMENT', 'REVIEW', 'USER') [not null]
  신고_대상_ID bigint [not null] // 게시물ID, 댓글ID, 리뷰ID, 사용자ID
  신고_카테고리 enum('SPAM', 'INAPPROPRIATE', 'HARASSMENT', 'COPYRIGHT', 'FAKE_INFO', 'OTHER') [not null]
  신고_사유 text [not null]
  첨부_증거_URLs json // 신고 증거 파일들
  처리_상태 enum('PENDING', 'REVIEWING', 'PROCESSED', 'REJECTED', 'CLOSED') [default: 'PENDING']
  처리_결과 enum('WARNING', 'CONTENT_HIDDEN', 'CONTENT_DELETED', 'USER_SUSPENDED', 'NO_ACTION') 
  처리_관리자_사용자ID bigint
  처리_메모 varchar(1000)
  처리일시 datetime
  신고일시 datetime [default: 'now()']
  생성일시 datetime [default: 'now()']
  수정일시 datetime [default: 'now()']
}

Table 북마크 {
  북마크ID bigint [pk, increment]
  사용자ID bigint [not null]
  게시물ID bigint [not null]
  폴더명 varchar(100) [default: '기본']
  생성일시 datetime [default: 'now()']
}

Table 알림 {
  알림ID bigint [pk, increment]
  수신자_사용자ID bigint [not null]
  발신자_사용자ID bigint
  알림_유형 enum('COMMENT', 'REPLY', 'LIKE', 'MENTION', 'SYSTEM', 'ORDER', 'REVIEW') [not null]
  알림_제목 varchar(200) [not null]
  알림_내용 varchar(500) [not null]
  관련_URL varchar(500) // 클릭시 이동할 URL
  읽음_여부 boolean [default: false]
  읽음일시 datetime
  생성일시 datetime [default: 'now()']
}
```

## 테이블 관계 (Foreign Key)

### 사용자 및 인증 관계
```sql
Ref: 주소.사용자ID > 사용자.사용자ID
Ref: 결제수단.사용자ID > 사용자.사용자ID
Ref: 로그인_이력.사용자ID > 사용자.사용자ID
```

### 농업 관리 관계
```sql
Ref: 농장.농장주_사용자ID > 사용자.사용자ID
Ref: 농장.주소ID > 주소.주소ID
Ref: 필드.농장ID > 농장.농장ID
Ref: 작물_재배.필드ID > 필드.필드ID
Ref: 작물_재배.작물_마스터ID > 작물_마스터.작물_마스터ID
Ref: 농업_활동.재배ID > 작물_재배.재배ID
Ref: 농업_활동.담당자_사용자ID > 사용자.사용자ID
Ref: 농업_자재.농장ID > 농장.농장ID
Ref: 자재_사용_이력.농업_활동ID > 농업_활동.활동ID
Ref: 자재_사용_이력.자재ID > 농업_자재.자재ID
```

### 상품 및 주문 관리 관계
```sql
Ref: 상품_카테고리.상위_카테고리ID > 상품_카테고리.카테고리ID
Ref: 상품.판매자_사용자ID > 사용자.사용자ID
Ref: 상품.카테고리ID > 상품_카테고리.카테고리ID
Ref: 상품_재고.상품ID > 상품.상품ID
Ref: 재고_이력.상품ID > 상품.상품ID
Ref: 재고_이력.관련_주문ID > 주문.주문ID
Ref: 재고_이력.담당자_사용자ID > 사용자.사용자ID
Ref: 장바구니.사용자ID > 사용자.사용자ID
Ref: 장바구니.상품ID > 상품.상품ID
Ref: 주문.주문자_사용자ID > 사용자.사용자ID
Ref: 주문.배송_주소ID > 주소.주소ID
Ref: 주문상품.주문ID > 주문.주문ID
Ref: 주문상품.상품ID > 상품.상품ID
Ref: 결제.주문ID > 주문.주문ID
Ref: 결제.결제수단ID > 결제수단.결제수단ID
Ref: 배송.주문ID > 주문.주문ID
```

### 커뮤니티 관리 관계
```sql
Ref: 게시판_카테고리.상위_카테고리ID > 게시판_카테고리.카테고리ID
Ref: 게시물.카테고리ID > 게시판_카테고리.카테고리ID
Ref: 게시물.작성자_사용자ID > 사용자.사용자ID
Ref: 게시물.최종_수정자_사용자ID > 사용자.사용자ID
Ref: 댓글.게시물ID > 게시물.게시물ID
Ref: 댓글.상위_댓글ID > 댓글.댓글ID
Ref: 댓글.작성자_사용자ID > 사용자.사용자ID
Ref: 좋아요.사용자ID > 사용자.사용자ID
Ref: 리뷰.주문상품ID > 주문상품.주문상품ID
Ref: 리뷰.작성자_사용자ID > 사용자.사용자ID
Ref: 리뷰.검토_관리자_사용자ID > 사용자.사용자ID
Ref: 리뷰_도움됨.리뷰ID > 리뷰.리뷰ID
Ref: 리뷰_도움됨.사용자ID > 사용자.사용자ID
Ref: 신고.신고자_사용자ID > 사용자.사용자ID
Ref: 신고.처리_관리자_사용자ID > 사용자.사용자ID
Ref: 북마크.사용자ID > 사용자.사용자ID
Ref: 북마크.게시물ID > 게시물.게시물ID
Ref: 알림.수신자_사용자ID > 사용자.사용자ID
Ref: 알림.발신자_사용자ID > 사용자.사용자ID
```

### 동적 참조 관계
```sql
// 좋아요 대상 동적 참조
// 좋아요.대상_ID는 대상_유형에 따라 다음을 참조:
// - 'POST' 유형: 게시물.게시물ID
// - 'COMMENT' 유형: 댓글.댓글ID
// - 'REVIEW' 유형: 리뷰.리뷰ID

// 신고 대상 동적 참조
// 신고.신고_대상_ID는 신고_유형에 따라 다음을 참조:
// - 'POST' 유형: 게시물.게시물ID
// - 'COMMENT' 유형: 댓글.댓글ID
// - 'REVIEW' 유형: 리뷰.리뷰ID
// - 'USER' 유형: 사용자.사용자ID
```

## 주요 특징

### 보안
- 카드번호, CVV는 암호화하여 저장
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

## 인덱스 최적화 권장사항

```sql
-- 검색 성능 향상을 위한 인덱스
CREATE INDEX idx_user_email ON 사용자(이메일);
CREATE INDEX idx_product_category ON 상품(카테고리);
CREATE INDEX idx_order_date ON 주문(주문일시);
CREATE INDEX idx_post_user_date ON 게시물(사용자ID, 작성일시);
CREATE INDEX idx_comment_post ON 댓글(게시물ID);
CREATE INDEX idx_review_product ON 리뷰(주문상품ID);
```