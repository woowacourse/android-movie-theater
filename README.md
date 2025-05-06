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

### 피드백 반영

- [x] HomeFragmentTest | 테스트 명 변경
- [x] Movie | UiModel 만들기
- [x] MovieItem | Movie 네이밍 변경
- [x] ScheduleTime | 메서드 분리
- [ ] Showings | 스스로 일을 할 수 있게 변경
- [ ] Ticket | 15 값 enum class로 변경
- [x] MovieSchedule | filter를 toList 전에 호출되도록 변경
- [x] MainActivity | commit 하는 부분 공통부분 메서드 분리
- [x] MainActivity | Fragment 의 전역으로 미리 생성하도록 변경
- [x] MainActivity | Fragment가 상태가 유지되도록 변경 (show, hide 사용)
- [x] HomeContract | navigate가 되는 책임 view에서만 담당하도록 변경
- [x] HomeFragment | 함수형 인터페이스로 변경
- [x] HomeFragment | 데이터 이동의 책임을 view -> presenter로 이동
- [ ] HomeFragment | findViewById 삭제
- [ ] HomeFragment | superCall의 필요성?
- [ ] MovieAdapter |  areItemsTheSame, areContentsTheSame 역할 찾기
- [ ] TheaterBottomSheetDialogFragment | 생성자 삭제
- [ ] 데이터 바인딩






