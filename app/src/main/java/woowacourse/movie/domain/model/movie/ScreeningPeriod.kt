package woowacourse.movie.domain.model.movie

import java.time.LocalDate

data class ScreeningPeriod(
    val startDate: LocalDate,
    val endDate: LocalDate,
) {
    init {
        require(!startDate.isAfter(endDate)) {
            INVALID_DATE_RANGE_MESSAGE.format(startDate, endDate)
        }
    }

    fun getAvailableDates(now: LocalDate): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var date = now
        while (!date.isAfter(endDate)) {
            dates.add(date)
            date = date.plusDays(INTERVAL_DAY)
        }
        return dates.filterNot { it.isBefore(startDate) }
    }

    companion object {
        private const val INTERVAL_DAY = 1L
        private const val INVALID_DATE_RANGE_MESSAGE =
            "시작 날짜는 종료 날짜 이후일 수 없습니다 (startDate: %s, endDate: %s)"
    }
}
