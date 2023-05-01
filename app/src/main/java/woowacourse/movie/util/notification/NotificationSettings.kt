package woowacourse.movie.util.notification

import androidx.annotation.DrawableRes
import woowacourse.movie.R

data class NotificationSettings(
    @DrawableRes val iconResId: Int = R.drawable.ic_launcher_foreground,
    val contentTitle: String,
    val contentText: String,
    val cancelable: Boolean
)
