package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.RunningTime
import woowacourse.movie.domain.model.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalTime

object DummyMovie {
    val dummyMovie =
        listOf(
            Movie(
                R.drawable.harrypotter_1.toString(),
                "해리 포터와 마법사의 돌",
                listOf(
                    ScreeningPeriod(
                        LocalDate.of(2025, 4, 1),
                        LocalDate.of(2025, 4, 30),
                        listOf(LocalTime.of(9,0,0), LocalTime.of(11,0,0)),
                        1,
                    ),
                    ScreeningPeriod(
                        LocalDate.of(2025, 4, 1),
                        LocalDate.of(2025, 4, 30),
                        listOf(LocalTime.of(10,0,0), LocalTime.of(12,0,0)),
                        2,
                    )
                ),
                RunningTime(152),
                listOf(Cinema(1, "선릉"), Cinema(2, "잠실"))
            )
        )

}

fun main() {
    val movie = DummyMovie.dummyMovie[0]
    val map = movie.screeningPeriods.groupBy {
        it.cinemaId
    }
    val a = map.keys.map { cinemaId ->
        movie.cinemas.find { it.id == cinemaId }
    }

    a.forEach {

    }
}