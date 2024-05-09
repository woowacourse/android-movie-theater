# android-movie-theater

## 1단계 기능 요구 사항

- [x] 영화 선택 후 극장을 선택할 수 있다.
    - [x] 바텀시트로 열린다.
- [x] 예매 완료 화면에서 극장 이름이 길 경우 말줄임표로 표시되어야 한다. (시안 참고)
- [x] 극장별로 상영하는 영화와 상영시간은 달라질 수 있다.

## 2단계 기능 요구 사항

- [x] 영화 예매 내역, 홈, 설정 화면으로 이동(navigate)할 수 있다.
  예매 내역 : 빈 화면
  홈 : 영화 리스트
  설정 : 빈 화면

## 3단계 기능 요구 사항

- [ ]: 예매 내역 리사이클러 뷰 만들기
  - [ ]: Adapter 
  - [ ]: ItemView xml
  - [ ]: ViewHolder
- [x]: Movie DB 만들기 
  - [x]: Reservation Table(=Entity) 만들기
  - [x]: Seat Table(=Entity) 만들기
  - [x]: dao
  - [x]: dao Test
  - [ ]: Mapper (Entity -> Domain Model)
  - [ ]: repository
- [ ]: 좌석 선택 뷰에서 예매 확인 시 Room 에 저장 하고 return 받은 id를 넘겨 주자
  - [ ]: 저장은 worker Thread, startActivity 는 ui Thread 에서 하자고~
  - [ ]: 기존 객체 직렬화 방식 -> id 넘겨 주는 방식으로 변경

### 4단계 요구 사항  

- 설정 에서 알림 기능을 On/Off 할 수 있다.
  - [ ]: SharedPref 에 알람 기능 껐다 켰다 어쩌구
- 영화 시작 시간 30분 전에 푸시 알림이 온다.
  - [ ]: Permission 설정
  - [ ]: Channel 생성 
  - [ ]: AlarmManager + BroadCast Receiver
- [ ]: 푸시 알림을 클릭 하면 예매 정보를 보여 준다.
  - [ ]: startActivity 로 Reservation 이동
- [ ]: Notification 커스터 마이징

- [ ]: 말리빈 요구 사항 반영 하기  

----  
시간 남으면 할 것

- [ ]: Looper + Handler 로 리팩

## 할 일

- [x]: dataBinding 적용
- [x]: test 코드 작성
- [x]: 화면 회전 대응
