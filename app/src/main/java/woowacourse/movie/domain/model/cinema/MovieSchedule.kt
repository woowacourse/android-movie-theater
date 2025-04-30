package woowacourse.movie.domain.model.cinema

import woowacourse.movie.domain.model.movie.Movie
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random

data class MovieSchedule(
    val movieId: Int,
    val times: List<LocalDateTime>,
) {
    fun getTimesAfter(time: LocalDateTime): List<LocalDateTime> = times.filter { it.isAfter(time) }

    companion object {
        fun createDummy(movie: Movie): MovieSchedule {
            val schedule = mutableListOf<LocalDateTime>()
            val dates = movie.screeningPeriod.getAvailableDates(LocalDate.now())
            dates.forEach { date ->
                val count = Random.nextInt(15)
                repeat(count) {
                    if (Random.nextBoolean()) schedule.add(LocalDateTime.of(date, LocalTime.of(it + 9, 0)))
                }
            }

            return MovieSchedule(movie.id, schedule)
        }
    }
}
