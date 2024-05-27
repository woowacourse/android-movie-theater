# android-movie-theater

## 기능 목록

- [x] 상영작 지금 예매를 누르면 해당 영화를 상영하는 영화관을 선택할 수 있다.
    - [x] 상영작을 상영하고 있는 영화관 정보를 불러온다.
    - [x] 상영작을 가진 각 영화관의 총 상영 시간 개수를 계산하여 보여준다.
    - [x] 모든 영화관 정보를 불러온다.
    - [x] 특정 영화관 정보를 불러온다.
    - [x] 상영작 지금 예매를 누르면 bottomSheet 가 나타난다.
        - [x] bottomSheet 는 극장 이름을 보여준다.
        - [x] bottomSheet 는 상영 시간 개수를 보여준다.
        - [x] 영화를 상영하는 영화관을 표시한다.
        - [x] 영화관 별로 영화의 상영시간을 보여준다.

- [x] 영화관을 선택하면 영화 예매(날짜, 시간, 개수를 정할 수 있는) 화면으로 이동.
- [x] 영화관 정보 필요
    - [x] `영화관 id`, `영화관 이름`, `상영작들`

- [x] 예매 완료 화면에서 극장 정보를 추가로 보여준다.
- [x] 예매 정보
    - [x] `영화관 이름` 이 추가되어야 함.

- [x] 영화 예매 내역, 홈, 설정 화면으로 이동(navigate)할 수 있다.
    - [x] 바텀 네비게이션을 보여준다
    - [x] 영화 예매 내역 화면
    - [x] 설정 화면
    - [x] 홈 화면(상영작 목록)

- [x] 좌석 선택 후 확인을 누르면 다이얼로그가 뜬다.

### step 3

- 예매 내역을 저장한다.
    - 데이터 베이스에 저장한다.
- 모든 예매 내역을 불러온다.
    - 예매 preview 내역은 날짜, 시간, 영화관 이름, 영화 제목을 보여준다.
    - 예매 상세 내역은 영화 제목, 날짜, 시간, 인원, 좌석, 영화관 이름, 결제 금액을 보여준다.

### step4

- 메인 화면에서 권한이 없을 경우
    - 가장 처음에는 권한 요청을 띄운다. (허용, 허용하지 않음)
    - 만약 권한 요청을 거부했을 경우에는 어떤 이유로 권한이 필요한지 안내하는 스낵바를 띄운다.

- 세팅 화면(SettingFragment)에서 푸시 알림을 설정할 수 있다.
    - 푸시 알림을 변경하려고 하면 환경 설정 앱에서 변경하라고 안내하는 스낵바를 띄운다.
    - 이 때 스낵바를 통해 환경 설정 앱에서 변경해야만 스위치가 토글된다. 해당 창에서 바로 스위치가 토글되지 않는다
  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU 일 때)
    - 

## 프로그래밍 요구사항

- DataBinding 을 사용해야 한다.


### refactor 마주친 이슈와 공부

- 좌석 예매

원래는 좌석을 예매하는 순간에 권한을 푸시 알림 확인받으려고 했음.
하지만 푸시 알림 권한이 없더라도, 좌서을 예매할 수 있어야 한다.  
그러니까 좌석을 예매 완료하고 나서, 권한을 받고, 권한을 수락 받으면, push 알림을 설정해주어야 한다.


- 좌석 ㅔ예매에서 onCreate 에서 requirePermission 했을 때

``` kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate: called ")
    initPresenter()
    initView()
    
    requestPermission()
    
    onBackPressedCallback()
}
```

``` kotlin
override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart: called")
    requestPermission()
}
```

이렇게 했을 때 위 둘 다 터짐.
영화 예매를 하고 넘어갈 때만 터짐.
대신, 영화 예매 내역에서 넘어갈 때는 안 터짐..
이유가 뭘까?

에러는 바인딩 어댑터 관련 에러임.
```agsl
 java.lang.IllegalStateException: not initialized seats: null
    at woowacourse.movie.ui.SeatsBindingAdapterKt.textUiFormat(SeatsBindingAdapter.kt:16)
```

이게 [에디가 이야기하고 찾아냈던 오류](https://fre2-dom.tistory.com/571)네. 바인딩 어댑터 수정해서 해결


