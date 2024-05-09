package woowacourse.movie

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class TheaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        val channelId = CHANNEL_ID
        val channelName = CHANNEL_NAME
        val descriptionText = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel =
            NotificationChannel(channelId, channelName, importance).apply {
                setShowBadge(true)
                description = descriptionText
            }
        getSystemService(NotificationManager::class.java).createNotificationChannel(mChannel)
    }

    companion object {
        const val CHANNEL_ID = "notification_channel_id"
        const val CHANNEL_NAME = "notification_channel"
        const val CHANNEL_DESCRIPTION = "예약 알림 채널 - 예약한 영화의 상영 시작 30분 전에 알림을 받을 수 있습니다."
    }
}
