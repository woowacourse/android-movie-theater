package woowacourse.movie.receiver

import android.app.PendingIntent

data class NotificationData(
    val title: String,
    val contextText: String,
    val smallIcon: Int,
    val isAutoCancel: Boolean,
    val pendingIntent: PendingIntent
)
