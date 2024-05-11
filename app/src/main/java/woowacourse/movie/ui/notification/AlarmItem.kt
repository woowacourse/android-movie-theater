package woowacourse.movie.ui.notification

import java.time.LocalDateTime

data class AlarmItem(
    val targetId: Long,
    val dateTime: LocalDateTime,
    val title: String,
    val subTitle: String,
)
