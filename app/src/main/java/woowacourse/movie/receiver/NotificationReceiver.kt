package woowacourse.movie.receiver

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.activity.MainActivity
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TicketsUiModel


//예약 알림을 수신하기 위한 리시버 클래스
class NotificationReceiver : BroadcastReceiver() {
    private val TAG = "wooseokring"

    //수신되는 인텐트 - The Intent being received.
    override fun onReceive(context: Context, intent: Intent) {
        Log.e(TAG, "onReceive 알람이 들어옴!!")
        val movieUiModel = intent.extras?.getSerializableCompat<MovieUiModel>(MOVIE_KEY_VALUE)
        val ticketsUiModel = intent.extras?.getSerializableCompat<TicketsUiModel>(TICKET_KEY_VALUE)
        Log.e(TAG, "onReceive contentValue값 확인 : $movieUiModel")


        //푸시 알림을 보내기위해 시스템에 권한을 요청하여 생성
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //안드로이드 오레오 버전 대응
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
            NotificationCompat.Builder(context, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(context)
        }

        //알림창 클릭 시 지정된 activity 화면으로 이동
        val intent2 = Intent(context, ReservationResultActivity::class.java)
        intent2.putExtra(MOVIE_KEY_VALUE, movieUiModel)
        intent2.putExtra(TICKET_KEY_VALUE, ticketsUiModel)
        // FLAG_UPDATE_CURRENT ->
        // 설명된 PendingIntent가 이미 존재하는 경우 유지하되, 추가 데이터를 이 새 Intent에 있는 것으로 대체함을 나타내는 플래그입니다.
        // getActivity, getBroadcast 및 getService와 함께 사용
        val pendingIntent = PendingIntent.getActivity(
            context, 123, intent2,
            PendingIntent.FLAG_IMMUTABLE
        )

        //알림창 제목
        builder.setContentTitle("예매 알림") //회의명노출
        builder.setContentText(movieUiModel?.title + "  30분 후에 상영"); //회의 내용
        //알림창 아이콘
        builder.setSmallIcon(R.drawable.ic_lock_idle_alarm)
        //알림창 터치시 자동 삭제
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)

        //푸시알림 빌드
        val notification: Notification = builder.build()

        //NotificationManager를 이용하여 푸시 알림 보내기
        manager.notify(1, notification)
    }

    companion object {
        //오레오 이상은 반드시 채널을 설정해줘야 Notification이 작동함
        private const val CHANNEL_ID = "channel1"
        private const val CHANNEL_NAME = "Channel1"
        private const val MOVIE_KEY_VALUE = "movie"
        private const val TICKET_KEY_VALUE = "tickets"
    }
}
