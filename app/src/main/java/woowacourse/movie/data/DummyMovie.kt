package woowacourse.movie.data

import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.RunningTime
import java.time.LocalDate

object DummyMovie {
    val dummyMovie =
        listOf(
            Movie(
                R.drawable.harrypotter_1.toString(),
                "해리 포터와 마법사의 돌",
                RunningTime(152),
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 30),
            ),
            Movie(
                R.drawable.harrypotter_2.toString(),
                "해리 포터와 마법사의 돌2",
                RunningTime(152),
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 6, 1),
            ),
            Movie(
                R.drawable.harrypotter_3.toString(),
                "해리 포터와 마법사의 돌3",
                RunningTime(152),
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
            Movie(
                R.drawable.harrypotter_4.toString(),
                "해리 포터와 마법사의 돌4",
                RunningTime(152),
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
            Movie(
                R.drawable.harrypotter_5.toString(),
                "해리 포터와 마법사의 돌5",
                RunningTime(152),
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
            Movie(
                R.drawable.harrypotter_6.toString(),
                "해리 포터와 마법사의 돌6",
                RunningTime(152),
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
            Movie(
                R.drawable.harrypotter_7.toString(),
                "해리 포터와 마법사의 돌7",
                RunningTime(152),
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
            ),
        )
}
