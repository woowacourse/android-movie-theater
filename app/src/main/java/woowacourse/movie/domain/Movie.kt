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
) : Serializable

val moviesDummy: List<Movie> =
    listOf(
        Movie(
            R.drawable.harry,
            "해리포터",
            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
            152,
        ),
        Movie(
            R.drawable.poster_suzume,
            "스즈메의 문단속",
            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
            152,
        ),
        Movie(
            R.drawable.poster_criminalcity,
            "범죄도시",
            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
            152,
        ),
        Movie(
            R.drawable.poster_castaway,
            "김씨표류기",
            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
            152,
        ),
        Movie(
            R.drawable.poster_singstreet,
            "싱스트리트",
            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
            152,
        ),
        Movie(
            R.drawable.poster_agugustrush,
            "어거스트 러쉬",
            Date(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 5, 30)),
            152,
        ),
    )
