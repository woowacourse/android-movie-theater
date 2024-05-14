package woowacourse.movie.model

import java.time.LocalDate

class MovieDate(val startLocalDate: LocalDate, val endLocalDate: LocalDate) {
    init {
        require(startLocalDate <= endLocalDate) { INVALID_DATE_RANGE_MESSAGE }
    }

    fun generateDates(): List<LocalDate> {
        return generateSequence(startLocalDate) { it.nextDay() }
            .takeWhile { !it.isAfter(endLocalDate) }
            .toList()
    }

    private fun LocalDate.nextDay() = plusDays(DATE_OFFSET)

    companion object {
        private const val INVALID_DATE_RANGE_MESSAGE = "영화 상영 날짜의 시작일은 종료일보다 이전이어야 합니다."
        private const val DATE_OFFSET = 1L
    }
}
