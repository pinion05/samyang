# samyang

Table 사용자 {
  사용자ID int [pk, increment]
  사용자이름 varchar(255) [not null, unique]
  비밀번호 varchar(255) [not null]
  이메일 varchar(255) [not null, unique]
  가입일 datetime [default: 'now()']
  관리자_여부 boolean [default: false]
  사용자정보_수정일 datetime [default: 'now()', not null]
  배송지_주소 varchar(255) [not null]
}

Table 관리자 {
  관리자ID int [pk, increment]
  사용자ID int [not null, unique] // 사용자 테이블과 1:1 관계
}

Table 결제수단 {
  결제수단ID int [pk, increment]
  사용자ID int [not null]
  카드_종류 varchar(50)
  카드번호_뒷4자리 varchar(4)
  만료일 date
  기본_결제수단_여부 boolean [default: false]
}

Table 작물 {
  작물ID int [pk, increment]
  사용자ID int [not null] // 이 작물이 특정 사용자의 것임을 나타냄
  작물명 varchar(255) [not null]
  파종일 date
  수확예정일 date
}

Table 농사일지 {
  일지ID int [pk, increment]
  사용자ID int [not null]
  작성일 datetime [default: 'now()']
  일기내용 text
  사진_URL varchar(255)
}

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
  주문일시 datetime [default: 'now()']
  총_결제금액 decimal(10, 2) [not null]
  상태 varchar(50) // 예: '주문 완료', '배송 중', '배송 완료', '취소'
}

Table 주문상품 {
  주문상품ID int [pk, increment]
  주문ID int [not null]
  상품ID int [not null]
  수량 int [not null]
}

Table 공지사항 {
  공지사항ID int [pk, increment]
  공지사항_이름 varchar(100) [not null, unique] // 예: '공지사항', '자유게시판'
  설명 text
  사진_URL varchar(255)
}

Table 게시물 {
  게시물ID int [pk, increment]
  사용자ID int [not null]
  제목 varchar(255) [not null]
  내용 text [not null]
  작성일 datetime [default: 'now()']
  수정일 datetime
}

Table 댓글 {
  댓글ID int [pk, increment]
  게시물ID int [not null]
  사용자ID int [not null]
  내용 text [not null]
  작성일 datetime [default: 'now()']
}

Table 신고 {
  신고ID int [pk, increment]
  신고자_사용자ID int [not null]
  신고_대상_유형 varchar(50) [not null] // '게시물', '댓글', '리뷰'
  신고_대상_ID int [not null]
  신고_사유 text [not null]
  신고일시 datetime [default: 'now()']
  처리_상태 varchar(50) [default: '접수'] // '접수', '처리 중', '처리 완료', '반려'
  제재_내용 text // 예: '7일 계정 정지'
}

Table 리뷰 {
  리뷰ID int [pk, increment]
  상품ID int [not null]
  사용자ID int [not null]
  주문상품ID int [not null, unique] // 한 주문상품에 하나의 리뷰만 가능
  평점 int [not null] // 1~5
  내용 text
  작성일 datetime [default: 'now()']
  이미지_URL varchar(255)
}


Ref: 사용자.사용자ID < 관리자.사용자ID
Ref: 사용자.사용자ID < 결제수단.사용자ID
Ref: 사용자.사용자ID < 작물.사용자ID
Ref: 사용자.사용자ID < 농사일지.사용자ID
Ref: 사용자.사용자ID < 주문.사용자ID
Ref: 사용자.사용자ID < 게시물.사용자ID
Ref: 사용자.사용자ID < 댓글.사용자ID
Ref: 사용자.사용자ID < 리뷰.사용자ID
Ref: 사용자.사용자ID < 신고.신고자_사용자ID

Ref: 관리자.관리자ID < 상품.관리자ID

Ref: 주문.주문ID < 주문상품.주문ID
Ref: 상품.상품ID < 주문상품.상품ID

Ref: 게시물.게시물ID < 댓글.게시물ID

Ref: 주문상품.주문상품ID < 리뷰.주문상품ID

// 신고 대상이 되는 테이블과의 관계는 유형별로 동적으로 참조됩니다.
// dbdiagram.io에서는 동적 참조를 명시하기 어렵지만, 개념적으로 연결됩니다.
// Ref: 신고.신고_대상_ID -> (게시물.게시물ID, 댓글.댓글ID, 리뷰.리뷰ID)