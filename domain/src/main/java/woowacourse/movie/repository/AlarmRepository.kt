package woowacourse.movie.repository

import java.time.LocalDateTime

interface AlarmRepository {
    fun putAlarms(localDateTimes: List<LocalDateTime>)

    fun deleteAllAlarms()
}
