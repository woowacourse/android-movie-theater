package woowacourse.movie.data

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import java.time.LocalDate

object MovieData {
    val HARRY_POTTER_01 =
        Movie(
            title = "해리 포터와 마법사의 돌",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 152,
        )

    val HARRY_POTTER_02 =
        Movie(
            title = "해리 포터와 비밀의 방",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 161,
        )

    val HARRY_POTTER_03 =
        Movie(
            title = "해리 포터와 아즈카반의 죄수",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 141,
        )

    val HARRY_POTTER_04 =
        Movie(
            title = "해리 포터와 불의 잔",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 157,
        )

    val HARRY_POTTER_05 =
        Movie(
            title = "해리 포터와 불사조 기사단",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 138,
        )

    val HARRY_POTTER_06 =
        Movie(
            title = "해리 포터와 혼혈 왕자",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 153,
        )

    val HARRY_POTTER_07 =
        Movie(
            title = "해리 포터와 죽음의 성물 - 1부",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 146,
        )

    val HARRY_POTTER_08 =
        Movie(
            title = "해리 포터와 죽음의 성물 - 2부",
            startDate = LocalDate.of(2025, 4, 1),
            endDate = LocalDate.of(2025, 5, 30),
            runningTime = 131,
        )

    private val data: Map<Movie, Int> =
        mapOf(
            HARRY_POTTER_01 to R.drawable.harry_potter_01,
            HARRY_POTTER_02 to R.drawable.harry_potter_02,
            HARRY_POTTER_03 to R.drawable.harry_potter_03,
            HARRY_POTTER_04 to R.drawable.harry_potter_04,
            HARRY_POTTER_05 to R.drawable.harry_potter_05,
            HARRY_POTTER_06 to R.drawable.harry_potter_06,
            HARRY_POTTER_07 to R.drawable.harry_potter_07,
            HARRY_POTTER_08 to R.drawable.harry_potter_08,
        )

    val movies: List<Movie> = data.keys.toList()

    @DrawableRes
    fun getDrawableResId(movie: Movie): Int = data[movie] ?: R.drawable.ic_launcher_foreground
}
