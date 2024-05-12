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
  - [x]: 32 일 때는 preference canNotify 를 true, 33 이상은 false 로 변경
### 리뷰 반영

- [x]: 스피너에 대한 일을 객체로 분리
- [x]: navigateToPurchaseConfirmation - MVP 방식으로 리팩토링
- [x]: presenter getter() 삭제
- [x]: InjectMockKs 애너테이션을 활용하여 테스트 코드 리팩토링
- [x]: assertion 함수들 assertSoftly 로 묶기  

- [x]: 푸시 알람 권한 요청 완전히 거절 시, 설정으로 이동하도록 변경
## 푸시 알람 권한

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
해당 함수를 사용하면 이미 한 번 거절한 권한인지 확인할 수 있다.
```kotlin
shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
```
true: 한 번 거절한 경우
false: 한 번 거절하지 않은 경우 (즉, 처음 요청한 경우, 2번 거절한 경우에도 false)

## SettingFragment Switch ON 눌렀을 때 액션 분기 처리

`shouldShowRequestPermissionRationale` 함수가 처음 거절한 경우와 2번 이상 거절한 경우 모두 true 를 반환하기에
`notificationPreference.hasBeenDeniedPermission` 변수를 추가하여 2번 이상 거절한 경우를 구분하였습니다! 

class 들로 역할 분리하는 것이 좋다고 생각하지만.. 시간이 없어 일단은 SettingFragment 내부에서 분기처리를 하였습니다.

1) API 32 이하인 경우, 권한이 있는 걍우 ---> Switch On 자유로움
```
Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU &&
hasAccessPermission()
```
2) 처음 권한 요청한 경우 ---> 권한 요청
```kotlin
// 1. 권한이 없음?
hasAccessPermission().not() &&
// 2. 2번째 권한 요청?
shouldShowRequestPermissionRationale(POST_NOTIFICATIONS).not() &&
// 3. 이미 거절당한 이력이 없니?
notificationPreference.hasBeenDeniedPermission.not()
```
3) 두 번째 권한 요청한 경우 ---> 권한 요청에 대한 설명 다이얼로그 띄우기
```kotlin
// 해당 함수로 한 번 거절한 이력이 있는지 확인
shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)
```
4) 완전히 거절한 경우 ---> 설정으로 이동
```kotlin
// 1. 권한이 없음?
hasAccessPermission().not() &&
// 2번째 권한 요청?
shouldShowRequestPermissionRationale(POST_NOTIFICATIONS).not() &&
// 3. 이미 거절당한 이력이 있니?
notificationPreference.hasBeenDeniedPermission
```

- 최종 분기처리문
```kotlin
when {
    Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> updateAlarmSwitch(true)
    isFirstRequest() -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
    isSecondRequest() -> {
        notificationPreference.hasBeenDeniedPermission = true
        explanationDialogForPushAlarm.show()
    }
    isCompletelyDenied() -> explanationDialogForNavigateToSetting.show()
    else -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
}
```
