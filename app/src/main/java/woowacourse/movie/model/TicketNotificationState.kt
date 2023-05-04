package woowacourse.movie.model

import androidx.annotation.DrawableRes
import woowacourse.movie.R

class TicketNotificationState(
    @DrawableRes val iconResId: Int = R.drawable.ic_launcher_foreground,
    val contentTitle: String,
    val contentText: String,
    val cancelable: Boolean
)
