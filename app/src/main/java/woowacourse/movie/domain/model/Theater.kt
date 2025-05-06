package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

class Theater(
    val name: String,
    val screenings: List<Screening>,
) {
    fun getTimesOnDate(
        movie: Movie,
        date: LocalDate,
    ): List<Int> {
        if (date.isBefore(movie.date.startDate) || date.isAfter(movie.date.endDate)) return emptyList()

        return screenings
            .filter { it.movie.title == movie.title }
            .flatMap { it.times }
    }

    fun getScreeningCount(
        movie: Movie,
        now: LocalDateTime,
    ): Int {
        val today = now.toLocalDate()
        val end = movie.date.endDate
        if (end.isBefore(today)) return 0

        return generateSequence(today) { current ->
            val next = current.plusDays(1)
            if (next.isAfter(end)) null else next
        }.sumOf { date ->
            val times = getTimesOnDate(movie, date)
            if (date == today) {
                times.count { it > now.hour }
            } else {
                times.size
            }
        }
    }
}
