package woowacourse.movie.presentation.alarm

import android.content.Context
import android.content.Intent

interface NotificationSender {
    fun send(
        context: Context,
        intent: Intent,
    )
}
