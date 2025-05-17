 # android-movie-theater

## 1단계 기능 요구 사항
- 영화 선택 후 극장을 선택할 수 있다.
- 예매 완료 화면에서 극장 이름이 길 경우 말줄임표로 표시되어야 한다. (시안 참고)
- 극장별로 상영하는 영화와 상영시간은 달라질 수 있다.
- 예를 들어 시간표를 다음처럼 구성할 수 있다.
```
선릉 극장
- 스타워즈(09시, 11시, 15시)
- 해리포터(13시, 15시, 17시, 19시)

잠실 극장
- 스즈메의 문단속(09시)
- 해리포터(10시, 12시, 14시)
- 범죄도시(11시, 13시)
```
  
## 1단계 구현할 기능 목록
- [x] 예매 버튼을 누르면 극장 선택 창이 뜬다.
- 극장 선택 화면
- [x] 극장 선택 화면(xml), item(xml) 구현
- [x] 해당 영화를 상영하는 극장이 보여야 한다.
- [x] 각 극장은 선택된 영화의 총 상영 시간의 개수가 보여야 한다.
- [x] 극장을 선택하면 영화 예매 화면으로 넘어간다.
- 예매 완료 화면
- [X] 예매 완료 화면(xml)에 극장 이름 추가
- [X] 상영 극장 이름을 화면에 출력한다.
- [X] 너무 긴 극장 이름은 줄임표를 사용하여 출력한다.
- 영화관
- [x] 영화관은 이름이랑 상영할 수 있는 영화 목록과 상영시간표를 가진다.
- [x] 영화관은 총 상영 개수를 반환할 수 있다.
- [x] 해당 영화 제목으로 상영시간표를 반환할 수 있다.
- 영화관들 (Theaters)
- [X] 영화관들(Theaters)는 모든 영화관을 가진다.
- [X] 영화관들은 해당 영화를 상영하는 영화관 리스트를 반환할 수 있다.

- 영화 메인 페이지 
  - [x] 데이터 바인딩
  - [X] drawable 리소스를 위한 데이터 바인딩 어댑터 구현
- [x] 극장 선택 화면 데이터 바인딩
- [x] 영화 예매 페이지 데이터 바인딩
- [x] 영화 좌석 페이지 데이터 바인딩
- 영화 예매 완료 페이지 
  - [x] 데이터 바인딩
  - [X] 영화 예매 시간 포맷을 위한 데이터 바인딩 어댑터 구현

## 2단계 기능 요구 사항
- 영화 예매 내역, 홈, 설정 화면으로 이동(navigate)할 수 있다.
  - 예매 내역 : 빈 화면 
  - 홈 : 영화 목록 
  - 설정 : 빈 화면

## 2단계 구현할 기능 목록
- [X] 예매 화면 Fragment 구현
- [X] 홈 화면 Fragment 구현
- [X] 설정 화면 Fragment 구현
- [X] bottomNavigationBar 구현
- [X] MovieActivity 구현

## 리팩토링할 부분
- [X] 좌석 선택 화면에서 인원 수 상관 없이 모두 선택되던 현상 수정

## 1,2단계 1차 피드백 반영
- [ ] 테스트 코드의 반복적인 내용을 테스트용 확장함수로 리팩토링
- matchText, isDisplay, click 등
- `fun ViewInteraction.performClick() = this.perform(click())`
- [ ] onCreate의 구현 코드 분리


## 1,2단계 변경 사항
- [x] fix: 매니페스트 오류 수정
- MovieActivity를 fragment로 변경하여 새로운 MainActivity를 만듦으로서 해당 사항 컴파일 에러 수정

- [x] refactor: Col 대신 Column으로 변경
- Column이라고 좀 더 명확하게 표현하도록 변경

- [x] test: 영화 취소 안내 메시지가 보이는지 확인하는 UI 테스트 코드 추가
- Intent로 넘겨야 하는 Test Fixture 추가
- AppCompatActivity -> Context로 변경

- [x] refactor: MovieBookedActivity UI 테스트 코드 추가
- 영화 제목, 영화 날짜와 시간, 인원 수, 좌석 정보, 티켓 총 가격이 보이는지 테스트 (극장 이름 제외)
- 앱 수준 gradle에 테스트 옵션 애니메이션 false 추가
- xml 아이템 ID에 booked 접미사 추가
- isDisplayed 확장함수 추가

- [x] refactor: MovieBookingActivity UI 테스트 코드 추가
- 영화 예매 화면의 포스터, 영화 제목, 상영 기간, 러닝 타임, 증가 버튼, 감소 버튼, 예매할 인원 수가 보이는지 테스트 (날짜와 시간 스피너 테스트 제외)
- xml 아이템 ID에 booking 접미사 추가

- [x] refactor: MovieBookingSeatActivity UI 테스트 코드 추가
- 영화관 스크린, 예매할 좌석, 영화 제목, 총 가격, 확인 버튼, 인원 수 만큼 좌석 선택 후 확인 다이얼로그가 보이는지 테스트
- xml 아이템 ID에 seat 접미사 추가

- [x] refactor: 테스트 함수용 확장 함수 파일 분리

- [x] refactor: onCreate 내 함수 분리
- MainActivity의 onCreate 함수 분리
- activity_main2.xml의 BottomNavigationView ID 수정 (nav_view -> navigation_view)
- MovieBookingSeatActivity onCreate 함수 분리
- MovieBookedActivity onCreate 함수 분리
- MovieFragment onCreateView 함수 분리

- [x] refactor: BindingAdapter 기능별 파일 분리
- TextViewBindingAdapter, ImageViewBindingAdapter

- [x] refactor: 다이얼로그 프래그먼트 생성 리팩토링
- TheaterBottomSheetDialogFragment에서 키를 관리하고 생성하도록 변경
- TAG 상수화

- [x] refactor: 클릭 함수를 인터페이스로 확장
- databinding xml 적용
- inner class MovieViewHolder, AdViewHolder 파일 분리 : 외부 참조가 강하게 연결되어 있는 것을 분리하기 위함

- [x] refactor: 레이아웃 매니저 xml에서 적용하도록 변경

- [x] fix: 이미지 안 뜨는 현상 수정

- [x] refactor: xml 레이아웃 아이디를 뷰 타입으로 갖도록 변경
- Sealed class로 FeedItem 정의


## 1,2단계 2차 변경 사항
- [x] refactor: 하드코딩된 text strings.xml로 변경

- [x] fix: Column의 검증 조건을 0 이상에서 1 이상으로 수정

- [x] refactor: therter에서 theater로 오타 수정

- [x] refactor: 객체 전달 오류 메시지 추가

- [x] refactor: 외부 리소스 사용 후 참조 해제하도록 변경

- [x] refactor: main2 네이밍 변경
- MainActivity -> HomeActivity로 변경
- activity_main2 -> activity_home으로 변경

- [x] refactor: Fragment Binding nullable 타입으로 변경
- onDestroyedView()에서 binding 참조 해제

- [x] refactor: binding clickListener 한 번만 할당하도록 변경
- 중복 익명 객체 제거

- [x] refactor: 피드아이템 생성 분리
- MVP 인터페이스 -> XXContract로 이름 변경

- [x] refactor: bottomSheet 뒤로가기 시 사라지도록 변경

- [x] test: UI 테스트 코드에 intent 값이 제대로 보이는지 추가


## 3단계 기능 요구 사항
내비게이션의 예매 내역 항목에 예매한 영화 목록을 보여준다.
- 예매 내역 : 예매한 영화 목록
- 홈 : 영화 목록 
- 설정 : 빈 화면

예매 내역 상세 
- 예매 내역을 터치하면 예매 정보를 보여준다. 
- 리스트 항목을 누르는 효과를 줘야 한다. (시안 참고)

- 앱을 재실행해도 기존 예매 내역이 유지되어야 한다.


## 3단계 구현할 기능 목록
예매 내역
- [x] 상영 날짜, 상영 시간, 상영 극장, 영화 제목을 가진다.
- [x] ReservationInfo를 예약 내역 목록에 보여준다.
- [x] 예매한 영화 내역이 목록에 보인다.
- [x] 아이템을 터치하면 클릭 효과가 보인다.
- [x] 누르면 예매 정보 화면을 볼 수 있다.
- [x] 재실행해도 기존 예매 내역이 유지된다.
- [x] 예매 내역은 날짜와 시간 순으로 정렬된다.


## 4단계 기능 요구 사항
설정에서 알림 기능을 On/Off 할 수 있다.
- 사용자가 앱을 재실행해도 설정 데이터가 남아있어야 한다.
  영화 시작 시간 30분 전에 푸시 알림이 온다.
  푸시 알림을 클릭하면 예매 정보를 보여준다.
  알림 권한 Dialog 및 Notification은 기본 UI를 그대로 사용한다.
- 단, Notification의 아이콘은 커스텀해본다.


## 4단계 구현할 기능 목록
- [x] 설정 화면 구현
- [x] 푸시 알림 여부 저장 기능
- [x] 푸시 알림을 위한 권한 허용 기능
- [x] 영화 시작 시간 30분 전에 푸시 알림 기능
- [x] 푸시 알림 클릭 시 예매 완료 화면으로 이동하는 기능
- [x] Notification 아이콘 변경

### 3, 4단계 변경 사항
- [x] refactor: BookingFragment 파일 구조 변경 및 ReservationFragment로 변경

- [x] refactor: movie_booked.xml ConstraintLayout 정리

- [x] refactor: Booked에서 BookingStatus, Theater을 ReservationInfo로 변경

- [x] refactor: 좌석 예매 화면에서 데이터베이스에 저장하고 완료 화면에서 uid로 데이터베이스로부터 값을 호출하도록 변경

- [x] chore: TestExtensions 파일명 오타 수정

- [x] fix: 마지막 아이템이 네비게이션 바에 안 보이던 현상 수정

- [x] refactor: 아이템별로 클릭 리스너를 설정했던 함수를 클릭 리스너를 설정하도록 변경

- [x] fix: 스피너 테스트 코드 수정
- 스피너 테스트 확장 함수 추가

- [x] refactor: TheaterViewHolder inner class에서 class로 변경

- [x] refactor: 코드 정리
- 오타 수정
- 사용자에게 보이는 문자열을 strings.xml에 정의
- 불필요한 코드 정리

- [x] refactor: permission 검증을 공통적으로 사용할 수 있도록 변경

- [x] refactor: Database 생성 방법 수정
- Application의 onCreate()에서 DB를 생성하고 ReservationRepository를 통해 DB에 접근하도록 수정

- [x] refactor: 프래그먼트 매니저에게 프래그먼트 위임
- 화면 회전 시 현재 프래그먼트가 유지되지 않고 홈으로 변경되던 현상 수정 -> 번들이 null일 때만 홈으로 설정

- [x] refactor: 채널 아이디 상수화

- [x] test: Room에 데이터를 저장하고 가져오는 Repository에 대한 테스트 추가

- [x] test: Room에 접근해 데이터를 가져오던 MovieBookedActivity에 대한 UI 테스트 코드 추가

- [x] refactor: RecyclerView.Adapter 속성 TheaterViewHolder 타입으로 한정

- [x] refactor: Receiver Intent 생성 분리 및 중복 코드 제거

- [x] refactor: findViewById -> binding.root로 변경

- [x] refactor: 리사이클러뷰홀더 -> ReservationViewHolder으로 타입 한정

- [x] refactor: getSharedPreferences 상위 함수로 이동

- [x] feat: 예약 정보를 찾을 수 없을 때 토스트 메시지와 함께 액티비티 종료
