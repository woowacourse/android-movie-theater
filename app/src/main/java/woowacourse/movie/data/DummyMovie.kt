package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.RunningTime
import woowacourse.movie.domain.model.Screening
import java.time.LocalDate
import java.time.LocalTime

object DummyMovie {
    val dummyMovie =
        listOf(
            Movie(
                R.drawable.harrypotter_1.toString(),
                "해리 포터와 마법사의 돌",
                RunningTime(152),
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5 ,30)
            ),
            Movie(
                R.drawable.harrypotter_2.toString(),
                "해리 포터와 마법사의 돌2",
                RunningTime(152),
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 6, 1)
            ),
            Movie(
                R.drawable.harrypotter_3.toString(),
                "해리 포터와 마법사의 돌3",
                RunningTime(152),
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30)
            )
        )

}
