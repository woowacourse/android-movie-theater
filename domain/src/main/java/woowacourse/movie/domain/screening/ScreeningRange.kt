package woowacourse.movie.domain.screening

import java.time.*

data class ScreeningRange(val startDate: LocalDate, val endDate: LocalDate) {

    init {
        require(startDate <= endDate) { START_DATE_AFTER_END_DATE_ERROR }
    }

    operator fun contains(dateTime: LocalDateTime): Boolean =
        dateTime.toLocalDate() in startDate..endDate


    companion object {
        private const val START_DATE_AFTER_END_DATE_ERROR = "시작 날짜는 마지막 날짜 이후일 수 없습니다."
    }
}
