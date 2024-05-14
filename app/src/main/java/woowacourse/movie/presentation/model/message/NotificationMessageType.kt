package woowacourse.movie.presentation.model.message

import android.content.Context
import woowacourse.movie.R

sealed class NotificationMessageType : MessageType {
    data object NotificationFailureMessage : NotificationMessageType() {
        override fun toMessage(context: Context): String = context.getString(R.string.notification_failure_message)
    }

    data object NotificationSuccessMessage : NotificationMessageType() {
        override fun toMessage(context: Context): String = context.getString(R.string.notification_success_message)
    }
}
