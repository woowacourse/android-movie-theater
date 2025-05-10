package woowacourse.movie.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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

        sender.send(context, intent)
    }
}
