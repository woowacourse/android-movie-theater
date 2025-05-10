package woowacourse.movie.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.data.NotificationRepositoryImpl
import woowacourse.movie.domain.NotificationRepository

class AlarmReceiver(
    private val repository: NotificationRepository = NotificationRepositoryImpl(),
    private val sender: NotificationSender = RemindNotificationSender(),
) : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!repository.notificationEnabled()) return

        runCatching {
            sender.send(context, intent)
        }.onFailure {
            Log.e(FAIL_SEND_NOTIFICATION_TAG, FAIL_SEND_NOTIFICATION, it)
        }
    }

    companion object {
        private const val FAIL_SEND_NOTIFICATION_TAG = "AlarmReceiver"
        private const val FAIL_SEND_NOTIFICATION = "알림 전송 실패"
    }
}
