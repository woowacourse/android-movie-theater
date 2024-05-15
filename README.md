# android-movie-theater

## 영화 티켓 예매

- [x] 여러 개의 영화 목록이 홈 화면에서 보인다.
    - [x] '지금 예매' 버튼을 클릭하면 극장을 선택하는 하단 다이얼로그를 표시한다.
        - [x] 하단 다이얼로그에는 극장명과 상영시간을 보여준다.
        - [x] 극장을 선택하면 영화 상세 페이지로 이동한다.

- [x] '+', '-' 버튼을 클릭해서 영화 예매 인원을 선택할 수 있다.
    - [x] 티켓 예매 인원은 1명 이상이다.

- [x] 예매 완료 페이지에서는 예매한 영화의 정보와 영화 예매 인원 정보, 가격, 극장명을 보여준다.

- [x] 상영 날짜와 상영 시간을 선택할 수 있다.
    - [x] 각 영화의 상영일은 각자의 범위를 갖는다(예: 2024.4.1 ~ 2024.4.28).
    - [x] 각 영화의 상영 시간은 극장별로 다르다.

- [x] '좌석 선택' 버튼을 클릭하면 좌석 선택 페이지로 이동한다.

- [x] 좌석은 총 5행 4열로 구성되어 있고 각 행은 알파벳, 열은 숫자로 표현한다.
    - 1, 2행은 B등급, 보라색 글자로 표시한다. (10,000원)
    - 3, 4행은 S등급, 초록색 글자로 표시한다. (15,000원)
    - 5행은 A등급, 파란색 글자로 표시한다. (12,000원)

- [x] 좌석을 선택하면 배경색이 바뀌고, 하단에 선택한 좌석 수를 반영한 최종 가격이 표시된다.
    - [x] 선택된 좌석을 재선택하면 선택이 해제된다.

- [x] '확인' 버튼을 클릭하면 최종 예매를 확인하는 다이얼로그가 표시되고 배경을 터치해도 사라지지 않는다.
    - [x] '예매 완료'를 선택하면 예약 완료 페이지로 이동한다.
    - [x] 예매 내역 항목에 예매한 영화가 저장된다.

- [x] 영화 목록에 영화가 세 번 노출될 때마다 광고가 한 번 노출된다.

- [x] 예매 내역 항목에 예매한 영화 리스트를 보여준다.
    - [x] 예매 내역을 터치하면 예매 정보를 보여준다.
    - [x] 리스트 항목을 누르는 효과를 줘야 한다.
    - [x] 앱을 재실행해도 기존 예매 내역이 유지되어야 한다.

- [x] 설정에서 알림 기능을 On/Off 할 수 있다.
    - [x] 사용자가 앱을 재실행해도 설정 데이터가 남아있어야 한다.

- [x] 영화 시작 시간 30분 전에 푸시 알림이 온다.

- [x] 푸시 알림을 클릭하면 예매 정보를 보여준다.
