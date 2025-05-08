**# android-movie-theater

## 📚️ 개요
사용자는 영화 선택 후 극장을 선택할 수 있다.

## 🛠️ 구현할 기능

- [x] 바텀 네비게이션에서 예매 내역, 홈, 설정 화면으로 이동할 수 있다
- [x] 기본 화면은 홈 화면이다
- [x] 극장 별로 상영하는 영화와 상영 시간은 달라질 수 있다
- [x] 각 극장은 영화 상영 시간표를 가진다

### 홈 (영화 목록)
- [x] 영화 목록을 볼 수 있다
- [x] 지금 예매 버튼을 누르면 극장 선택 다이얼로그가 나온다

### 극장 선택
- [x] 각 극장의 이름과 오늘 현재 시간 이후의 상영 횟수를 볼 수 있다
- [x] 극장을 선택하면 영화 예매 화면으로 이동한다


### 영화 예매
- [x] 원하는 상영일을 선택할 수 있다
  - [x] 각 영화의 상영일은 영화의 개봉일과 종료일 사이의 날짜로 구성된다 
- [x] 원하는 상영 시간을 선택할 수 있다
- [x] 예약할 인원을 선택할 수 있다
- [x] 선택 완료 버튼을 누르면 좌석 선택 화면으로 이동한다
- [x] 화면이 회전되어도 입력한 정보는 유지되어야 한다


### 영화 예매 - 좌석
- [x] 구매할 좌석을 선택할 수 있다
- [x] 좌석을 선택하면 선택된 좌석은 색이 변경된다
- [x] 좌석을 선택/해제할 때마다 선택된 좌석의 가격을 자동으로 업데이트한다
- [x] 좌석의 종류(B, S, A 등)에 따라 가격이 다르게 표시된다
- [x] 앞에서 선택한 인원만큼의 좌석을 선택할 수 있다
- [x] 선택된 좌석이 있는 경우에만, 예매 완료 화면으로 이동할 수 있다
- [x] 예매 완료를 확인하는 다이얼로그가 표시되고 배경을 터치해도 사라지지 않는다
- [x] 다이얼로그에서 예매 완료를 선택하면 예매 내역 화면으로 이동한다
- [x] 화면이 회전되어도 입력한 정보는 유지되어야 한다


### 영화 예매 - 완료
- [x] 취소 안내 문구를 보여준다
- [x] 영화 제목과 선택한 날짜, 시간을 보여준다
- [x] 관람 인원과 선택한 좌석 정보를 보여준다
- [x] 영화 극장이름을 보여준다
- [x] 티켓의 총 가격을 보여준다

## 피드백 반영
- [x] Theater | 더미 데이터 분리 
- [ ] Showings | 구조 개선 고안
- [x] MainActivity | BotNav 중복 코드 제거 
- [x] HomeFragment | Fragment 생명 주기에 따른 binding 해제 관련
- [x] HomeFragment | 광고 반복 로직 비지니스 로직으로 변경
- [x] HomeFragment | 사용하지 않는 파라미터 제거
- [x] HomeFragment | 생성자로 데이터 주입받기
- [x] HomeFragment | 반복되는 뷰 함수 호출 로직 개선
- [x] TheaterAdapter | DiffUtil 함수 확인 후 개선 
- [ ] TheaterViewHolder | 바인딩 전달하는 파라미터 수정
- [x] TheaterBottomSheetDialogFragment | getSerializable 메서드 수정
- [x] TheaterBottomSheetDialogFragment | 바텀 시트를 띄운 상태로 구성 변경 시, 앱 크래시가 나지 않도록 개선
- [x] TheaterBottomSheetDialogFragment | 뷰 바인딩 적용
- [x] ReservationActivity | SDK 관련 로직 확장 함수로 사용
- [x] ReservationSeatPresenter | Seat 자료 구조형 불변으로 변경
- [x] ReservationSeatPresenter | Presenter에서 안드로이드 의존성 제거
- [x] ReservationSeatPresenter | getSerializable 메서드 수정
- [x] movie_item | text에 값이 안들어있는 오류 수정
