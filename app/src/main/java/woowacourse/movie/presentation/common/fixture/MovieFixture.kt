package woowacourse.movie.presentation.common.fixture

import woowacourse.movie.R
import woowacourse.movie.domain.model.cinema.MovieSchedule
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.Poster
import woowacourse.movie.domain.model.movie.RunningTime
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random

val dummyMovie =
    Movie(
        1,
        "해리 포터와 마법사의 돌",
        Poster.Resource(R.drawable.harrypotter),
        ScreeningPeriod(
            LocalDate.now(),
            LocalDate.now().plusMonths(1),
        ),
        RunningTime(152),
    )

fun createDummyMovies(count: Int): List<Movie> = List(count) { dummyMovie.copy(id = it, title = "해리 포터와 마법사의 돌 $it") }

fun createDummySchedule(movie: Movie): MovieSchedule {
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
