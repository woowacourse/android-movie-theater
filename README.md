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

- [x]: 예매 내역 리사이클러 뷰 만들기
- [x]: Adapter
- [x]: ItemView xml
- [x]: ViewHolder
- [x]: Movie DB 만들기
- [x]: Reservation Table(=Entity) 만들기
- [x]: Seat Table(=Entity) 만들기
- [x]: dao
- [x]: dao Test
- [x]: Mapper (Entity -> Domain Model)
- [x]: repository
- [x]: 좌석 선택 뷰에서 예매 확인 시 Room 에 저장 하고 return 받은 id를 넘겨 주자
- [x]: 저장은 worker Thread, startActivity 는 ui Thread 에서 하자고~
- [x]: 기존 객체 직렬화 방식 -> id 넘겨 주는 방식으로 변경
- [x]: 예매 결과 뷰 dataBinding 적용
- [x]: 예매 결과 뷰 id로 예매 정보 가져오기

### 4단계 요구 사항

- 설정 에서 알림 기능을 On/Off 할 수 있다.
  - [x]: SharedPref 에 푸시 알림 여부를 저장 한다.
- 영화 시작 시간 30분 전에 푸시 알림이 온다.
  - [x]: Channel 생성
  - [x]: AlarmManager + BroadCast Receiver
  - [x]: 푸시 알림을 클릭 하면 예매 정보를 보여 준다.
  - [x]: startActivity 로 Reservation 이동
  - [x]: Notification 커스터 마이징

- 푸시 알림 퍼미션 로직
-
    1) Home - 맨 처음에만 퍼미션 체크를 함
-
    2) Setting

    - 1번째 요청 아무 것도 없음
    - 2번째 요청에만 Explanation Dialog 보여줌
    - 3번째 요청부터도 아무 것도 없음 (이게.. 1, 3 번째 구분하는게 모르겠음)

### 리뷰 반영

- [ ]: 말리빈 요구 사항 반영 하기

----  
시간 남으면 할 것

- [ ]: Looper + Handler 로 리팩

## 할 일

- [x]: dataBinding 적용
- [x]: test 코드 작성
- [x]: 화면 회전 대응

## Study

안드로이드 13부터 푸시 알람 권한을 동적으로 받아야한다.
안드로이드 12는 확인 X
해당 함수를 사용하면 권한이 있는지 확인할 수 있다.
false: 권한이 없는 경우
true: 권한이 있는 경우

```kotlin
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun hasNotificationPermission(): Boolean = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.POST_NOTIFICATIONS
) == PackageManager.PERMISSION_GRANTED
```

사용자가 한 번 거절하면, 다시 요청할 때 권한 요청에 대한 이유를 설명해야 하는걸 권장한다.
true: 처음 요청하는 경우, 1번 이미 거절한 경우
false: 권한이 있는 경우, 2번 거절한 경우

```kotlin
shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
```