# android-movie-ticket

## step1

### UI
- [x] 영화를 리스트에 보여준다.
  - [x] 영화 예매 버튼을 누르면 상세 정보 화면으로 이동한다.
- [x] 영화의 상세 정보를 보여준다.
  - [x] 예약할 인원을 선택할 수 있다.
  - [x] 예매 완료 버튼을 누르면 예약 완료 화면으로 이동한다.
  - [x] 뒤로가기 버튼을 누르면 영화 목록 화면으로 이동한다.
- [x] 최종 예약 정보를 보여준다.
  - [x] 영화 결제 금액을 보여준다.
  - [x] 영화 정보를 보여준다.
  - [x] 뒤로가기 버튼을 누르면 영화 목록 화면으로 이동한다.

### Domain
- [x] 구매할 티켓에 대한 가격을 계산한다.

## step2

### UI
- [x] 영화 상영일 기간을 보여준다.
  - [x] 영화의 상영일은 각자의 범위를 갖는다.
- [x] 영화 상세 정보 화면에서 날짜와 시간을 선택할 수 있다.
- [x] 최종 예약 정보를 보여준다.
  - [x] 영화 상영 날짜와 시간을 보여준다.
  - [x] 할인된 영화 금액을 보여준다.
- [x] 화면이 회전되어도 입력한 정보를 유지한다.
- [x] 영화 예매 화면은 뒤로가기 버튼을 지원한다.

### Domain
- [x] 주말은 오전 9시, 평일은 오전 10시부터 자정까지 두 시간 간격으로 상영한다.
  - [x] 현재 시간 이전의 정보는 제외한다.
  - [x] 현재 날짜 이전의 정보는 제외한다.
  - [x] 시간 기본값은 현재 시간 바로 직후이다.
  - [x] 날짜 기본값은 금일이다.
- [x] 할인 조건에 따라 적절한 할인 정책이 적용된다.
  - [x] 무비데이(매월 10, 20, 30일)일 때: 10% 할인
  - [x] 조조(11시 이전)/야간(20시 이후)일 때: 2,000원 할인
  - [x] 두 조건은 겹칠 수 있고 무비데이 할인이 선적용되어야 한다.
- [x] 영화 티켓은 최소 1장, 최대 100장이다.

## step3

### UI
- [x] 좌석의 각 행은 알파벳, 열은 숫자로 표현한다.
  - [x] TableLayout을 사용한다.
  - [x] 좌석 수 변경에 대비하여 동적으로 좌석을 추가할 수 있다.
- [x] 좌석을 선택하면 배경색이 바뀌고, 하단에 할인정책과 좌석 등급을 고려한 최종 가격이 표시된다.
- [x] 선택된 좌석을 재선택하면 선택이 해제된다.
- [x] 최종 예매를 확인하는 다이얼로그가 표시되고 배경을 터치해도 사라지지 않아야 한다.
- [x] 좌석 등급에 따라 글자색이 다르다.
  - [x] 1, 2행은 보라색 글자이다.
  - [x] 3, 4행은 초록색 글자이다.
  - [x] 5행은 초록색 글자이다.
- [x] 티켓 수까지만 선택할 수 있고, 그 이전에는 확인 버튼이 비활성화 상태이다.

### Domain
- [x] 좌석은 총 5행 4열로 구성되어 있다.
  - [x] 1행 보다 작으면 예외가 발생한다.
  - [x] 1열 보다 작으면 예외가 발생한다.
- [x] 등급 정책은 다음과 같다.
  - [x] 1, 2행은 B등급이다. (10,000원)
  - [x] 3, 4행은 S등급이다. (15,000원)
  - [x] 5행은 A등급이다. (12,000원)

### Programming
- [x] 기능 요구 사항에 대한 UI 테스트를 작성해야 한다.

# android-movie-theater

### UI
- [x] 네비게이션으로 영화 예매 앱의 기능을 사용할 수 있다.
  - [x] 예매 내역 : 예매한 영화 리스트
  - [x] 홈 : 영화 리스트
  - [x] 설정 : 빈 화면
- [x] 예매 내역(예매한 영화 리스트) 상세
  - [x] 예매 내역을 터치하면 예매 정보를 보여준다.
  - [x] 리스트 항목을 누르는 효과를 줘야 한다. (시안 참고)
- [x] 알림권한 허용을 요청한다.
- [ ] 설정에서 알림 기능을 On/Off 할 수 있다.
  - [ ] 영화 시작 시간 30분 전에 푸시 알림이 온다.
  - [ ] 푸시 알림을 클릭하면 예매 정보를 보여준다.

### Data
- [x] 사용자가 앱을 재실행해도 설정 데이터가 남아있어야 한다.
  - [x] SharedPreference를 사용한다.
