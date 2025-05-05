package woowacourse.movie.sample

import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.ScreeningPeriod
import java.time.LocalDate

val DUMMY_MOVIES =
    mapOf(
        1L to
            Movie(
                1L,
                "해리 포터와 마법사의 돌",
                R.drawable.harry_potter_one,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 5, 30),
                ),
                152,
            ),
        2L to
            Movie(
                2L,
                "해리 포터와 비밀의 방",
                R.drawable.harry_potter_two,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 5, 28),
                ),
                162,
            ),
        3L to
            Movie(
                3L,
                "해리 포터와 아즈카반의 죄수",
                R.drawable.harry_potter_three,
                ScreeningPeriod(
                    LocalDate.of(2025, 5, 1),
                    LocalDate.of(2025, 5, 31),
                ),
                141,
            ),
        4L to
            Movie(
                4L,
                "해리 포터와 불의 잔",
                R.drawable.harry_potter_four,
                ScreeningPeriod(
                    LocalDate.of(2025, 6, 1),
                    LocalDate.of(2025, 6, 30),
                ),
                157,
            ),
        5L to
            Movie(
                5L,
                "어바웃 타임",
                R.drawable.about_time,
                ScreeningPeriod(
                    LocalDate.of(2025, 5, 6),
                    LocalDate.of(2025, 5, 12),
                ),
                152,
            ),
        6L to
            Movie(
                6L,
                "이터널 션샤인",
                R.drawable.eternal_sunshine,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 29),
                    LocalDate.of(2025, 5, 12),
                ),
                159,
            ),
        7L to
            Movie(
                7L,
                "라이프 오브 파이",
                R.drawable.life_of_pi,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 29),
                    LocalDate.of(2025, 4, 30),
                ),
                159,
            ),
        8L to
            Movie(
                8L,
                "타이타닉",
                R.drawable.titanic,
                ScreeningPeriod(
                    LocalDate.of(2025, 5, 1),
                    LocalDate.of(2025, 5, 17),
                ),
                170,
            ),
    )
