package woowacourse.movie.feature.setting.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.feature.result.MovieResultActivity
import woowacourse.movie.util.MovieIntentConstant.DEFAULT_VALUE_NOTIFICATION_DESCRIPTION
import woowacourse.movie.util.MovieIntentConstant.DEFAULT_VALUE_NOTIFICATION_TITLE
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_TICKET_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_NOTIFICATION_DESCRIPTION
import woowacourse.movie.util.MovieIntentConstant.KEY_NOTIFICATION_TITLE
import woowacourse.movie.util.MovieIntentConstant.KEY_TICKET_ID
import woowacourse.movie.feature.setting.notification.NotificationRegister

class TicketAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationRegister = NotificationRegister(context)
        val newIntent =
            MovieResultActivity.newIntent(
                context,
                intent.getLongExtra(KEY_TICKET_ID, INVALID_VALUE_TICKET_ID),
            )
        notificationRegister.deliverNotification(
            newIntent,
            intent.getStringExtra(KEY_NOTIFICATION_TITLE) ?: DEFAULT_VALUE_NOTIFICATION_TITLE,
            intent.getStringExtra(KEY_NOTIFICATION_DESCRIPTION)
                ?: DEFAULT_VALUE_NOTIFICATION_DESCRIPTION,
        )
    }
}
