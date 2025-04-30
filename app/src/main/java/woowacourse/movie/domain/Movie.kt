package woowacourse.movie.domain

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.domain.movietime.Date
import java.io.Serializable
import java.time.LocalDate

data class Movie(
    @DrawableRes val image: Int,
    val title: String,
    val date: Date,
    val time: Int,
) : Serializable {
    companion object {
        val dummy: List<Movie> =
            listOf(
                Movie(
                    R.drawable.harry,
                    "해리포터",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                    152,
                ),
                Movie(
                    R.drawable.harry,
                    "스즈메의 문단속",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                    152,
                ),
                Movie(
                    R.drawable.harry,
                    "범죄도시",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                    152,
                ),
                Movie(
                    R.drawable.harry,
                    "스타워즈",
                    Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30)),
                    152,
                ),
            )
    }
}
