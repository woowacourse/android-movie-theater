package woowacourse.movie.domain.model

import java.time.LocalDateTime

interface AlarmTime {
    fun calculated(
        dateTime: LocalDateTime,
        alarmTimeMinutesBefore: Int,
    ): Long?
}
