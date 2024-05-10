# android-movie-theater

## 도메인 

- Theater
  - [x] 영화를 선택하면 극장을 선택할 수 있다.
  - [x] 영화 리스트는 각 상영 시간을 가진다.
  - [x] 상영시간은 주말과 평일로 나뉜다.
  - [x] 상영 시간
    - [x] 평일은 오전 10시부터 자정까지 시간 범위를 가진다.
    - [x] 주말은 오전 9시부터 자정까지 시간 범위를 가진다.

## 안드로이드 기능 명세

- [x] TheaterSelectionFragment
  - [x] 원하는 극장을 선택할 수 있다.
  - [x] 극장 리스트를 보여준다.
  - [x] 극장을 선택하면 ReservationFragment로 이동한다.
- [x] ReservationFragment
  - [x] 상영일을 선택할 수 있다
  - [x] 상영 시간을 선택할 수 있다
  - [x] 마이너스 버튼을 1씩 티켓 수가 감소한다
    - [x] 최소 1장 이상 구매 가능 (범위를 벗어나면 알려준다)
  - [x] 플러스 버튼을 1씩 티켓 수가 증가한다
    - [x] 최대 20장 까지만 구매 가능 (범위를 벗어나면 알려준다)
  **좌석 선택 버튼**
  - [x] 영화 예매에서 좌석 선택 버튼을 누르면 영화 좌석 선택 화면으로 이동한다
    - [x] 이동 시 티켓 및 영화 정보 전달

- [x] SeatSelectionFragment
    - **액션바**
  - [x] 뒤로 가기 버튼을 누르면 예매 화면으로 이동한다

  **좌석 선택**
  - [x] 좌석 배치도를 보여준다
  - [x] 좌석을 선택하면 배경색이 바뀐다
    - [x] 선택된 좌석을 재선택하면 선택이 해제된다
  - [x] 선택한 좌석 수를 반영한 최종 가격이 표시된다
  - [x] 1, 2행은 B등급, 보라색 글자로 표시한다
  - [x] 3, 4행은 S등급, 초록색 글자로 표시한다
  - [x] 5행은 A등급, 파란색 글자로 표시한다 (12,000원)

  **확인 버튼**
  - [x] 확인 버튼의 기본 상태는 비활성화이다
  - [x] 선택한 인원 수와 선택한 좌석의 수가 동일할 때만 확인 버튼을 활성화한다
    - [x] 확인 버튼을 누르면 예매 확인 다이얼로그를 띄운다

  **예매 확인 다이얼로그**
  - [x] 예매 확인 다이얼로그는 취소, 예매 완료 버튼을 가지고 있다
    - [x] 취소 버튼을 누르면 좌석 선택 화면에서 다이얼로그만 사라진다
    - [x] 예매 완료 버튼을 누르면 다이얼로그가 사라지고, 티켓 화면으로 이동한다
    - [x] 티켓 화면으로 이동할 때 티켓 정보를 DB에 저장한다.
    - [x] 배경을 터치 했을 때는 다이얼로그가 사라지지 않는다
      - [x] 이동 시 티켓을 전달한다

- [x] ReservationFinishedFragment
  **액션바**
   - [x] 뒤로 가기 버튼을 누르면 상영표 화면으로 이동한다

  **티켓 정보**
  - [x] 예매 취소 안내 문구를 보여준다
  - [x] 티켓 정보를 보여준다
    - [x] 제목
    - [x] 상영 일시
    - [x] 관람 인원
    - [x] 좌석 번호
    - [x] 결제 금액
  - [x] 극장 이름을 보여준다
    - [x] 극장 이름이 길면 말줄임표로 표시한다.

- [x] HomeFragment
  **상영표**
  - [x] 리스트 뷰는 영화 종류를 보여준다
    - [x] 영화가 세 번 노출될 때마다 광고가 한 번 노출된다
  - [x] 상영표에서 지금 예매 버튼을 누르면 예매 화면으로 이동한다
    - [x] 이동 시 선택된 아이템에 해당하는 영화 정보를 전달한다

- [x] ReservationHistoryFragment
  - [x] 예매내역을 보여준다
  - [x] 예매내역 item은 상영일, 상영 시간, 극장, 영화 제목을 보여준다
  - [x] 예매내역 item을 클릭하면 예매 정보 화면으로 이동한다

- [ ] SettingFragment
  - [ ] 알림 기능을 On/Off 할 수 있다.
  - [ ] 앱을 재실행해도 설정 데이터가 남아있다.
  - [ ] 영화 시작 시간 30분 전에 푸시 알림이 온다.
  - [ ] 푸시 알림을 클릭하면 예매 정보를 보여준다.
