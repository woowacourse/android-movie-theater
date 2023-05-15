package woowacourse.movie.domain.screening

import woowacourse.movie.domain.theater.Theater
import java.time.LocalTime

data class TimeTable(val timeTable: Map<Theater, List<LocalTime>>) {

    fun screenOn(theater: Theater, time: LocalTime): Boolean =
        time in (timeTable[theater] ?: listOf())
}