# android-movie-theater

## 기능 요구 사항

- [x] bottomNavigationView를 만든다.
    - [x] 예매 내역
    - [x] 홈
    - [x] 설정
- [x] 바텀 내비게이션 탭을 누르면 화면을 이동한다.

## step2 리뷰 반영

- [x] 변수명 및 레이아웃 ID 개선
- [x] 좌석 등급 enum 적용
- [x] 영화 목록 화면
    - [x] 바인딩 클래스 사용으로 코드 간결화 (`DataBindingUtil` 제거)
    - [x] 중복 Fragment 생성 방지 로직 추가
    - [x] SAM 인터페이스 적용으로 클릭 리스너 개선
- [x] 변경된 로직의 테스트 추가
    - [x] `MovieDao` 테스트 코드 작성 및 구조 개선
