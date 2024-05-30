# refactor 마주친 이슈와 공부

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

- 브로드 캐스트 리시버 관련

_리시버가 알림을 설정하는 것까지 하다보니 다소 무겁게 느껴지네요.
알람 기능에 대해서도 별도 객체로 분리 해 볼 수 있지 않을까요?_

notificaiton 을 만들어서 notify 하는 부분을 분리하고, broadcastreceiver 에서 onReevie 할 때 이 notify 메서드를 호출하도록 함.

``` kotlin
interface NotificationRepository {
    fun createNotification(channelId: String, channelName: String, importance: Int)

    fun buildNotification(pendingIntent: PendingIntent, channelId: String): Notification

    fun notify(requestCode: Int, notification: Notification)
}
```

''' kotlin
class DefaultNotificationRepository(private val context: Context) : NotificationRepository {
override fun createNotification(channelId: String, channelName: String, importance: Int) {
val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
val channel = NotificationChannel(channelId, channelName, importance)
notificationManager.createNotificationChannel(channel)
}

    override fun buildNotification(pendingIntent: PendingIntent, channelId: String): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getString(R.string.movie_reservation_reminder_title))
            .setContentText(context.getString(R.string.movie_reservation_reminder_content, 30))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    override fun notify(notificationId: Int, notification: Notification) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        private const val ALARM_TIME_MINUTE = 30

        private const val MOVIE_RESERVATION_REMINDER_CHANNEL_ID = "movie_reservation_reminder_channel_id"
        private const val MOVIE_RESERVATION_REMINDER_CHANNEL_NAME = "movie_reservation_reminder_channel_name"
        const val MOVIE_RESERVATION_REMINDER_REQUEST_CODE = 101
    }

}

```

''' kotlin
class PushNotificationBroadCastReceiver(
    private val notificationRepository: NotificationRepository
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reservationTicketId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, DEFAULT_RESERVATION_TICKET_ID)
        val pendingIntent = createPendingIntent(context, reservationTicketId)

        notificationRepository.createNotification(
            MOVIE_RESERVATION_REMINDER_CHANNEL_ID,
            MOVIE_RESERVATION_REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notification =
            notificationRepository.buildNotification(pendingIntent, MOVIE_RESERVATION_REMINDER_CHANNEL_ID)
        notificationRepository.notify(MOVIE_RESERVATION_REMINDER_REQUEST_CODE, notification)
    }

    private fun createPendingIntent(context: Context, reservationTicketId: Int): PendingIntent {
        val mainIntent = Intent(context, MainActivity::class.java)
        val notificationIntent = Intent(context, ReservationCompleteActivity::class.java).apply {
            putExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, reservationTicketId)
        }
        val stackBuilder = TaskStackBuilder.create(context).apply {
            addParentStack(MainActivity::class.java)
            addNextIntentWithParentStack(mainIntent)
            addNextIntent(notificationIntent)
        }

        return stackBuilder.getPendingIntent(
            MOVIE_RESERVATION_REMINDER_REQUEST_CODE,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
            ?: throw IllegalStateException("Cannot build PendingIntent for context: $context, reservationTicketId: $reservationTicketId")
    }


    companion object {
        private const val MOVIE_RESERVATION_REMINDER_CHANNEL_ID = "movie_reservation_reminder_channel_id"
        private const val MOVIE_RESERVATION_REMINDER_CHANNEL_NAME = "movie_reservation_reminder_channel_name"
        const val MOVIE_RESERVATION_REMINDER_REQUEST_CODE = 101
    }
}

```

그런데 이렇게 하면, `NotificationRepository` 자체가 android.app.Notification 과 android.app.PendingIntent 와 의존성이 생기네..
그러면 원시타입만 갖는 createNotification 만 만들어야 겠다.

거기다가 스케줄 기능도 더 도메인 쪽으로 더 숨기자.
완전 계산하는, 비즈니스 로직이니까.

- 영화 예매 완료 액티비티에서 푸시 알림 설정

이제 영화 예매 완료 시에 푸시 알림을 설정한다
그런데 영화 예매 화면에 갈 때마다 계속 알림이 뜬다.
그러면 어떻게 해야 하지...?

먼저 영화 예매 액티비티, 프레젠터를 보자

isAlready DefaultNotificationRepository 의 isAlarmAlreadyRegistered 메서드에서 확인하고, 이미 등록되어 있으면, 더 이상 등록하지 않도록 함.

지금 예매 때 register 호출, 그리고 다시 내역에서 들어가서 한번더 register 호출함.

그런데 이제 아예 알람을 호출 안하네?

일단 한번만 호출하면 제대로 알림 등록 함.

아니 왜 영화 시작 30분 전이 아니라 그냥 바로 알림을 줘버려??

앱을 지우고,1시 44분에 2시 30분 예매 ㄱㄱ

1시 45분에 바로 알람이 뜨네?
