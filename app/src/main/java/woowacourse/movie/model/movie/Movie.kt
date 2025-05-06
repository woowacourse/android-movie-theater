package woowacourse.movie.model.movie

import woowacourse.movie.R
import java.io.Serializable
import java.time.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val poster: Int,
    val movieDate: MovieDate,
    val runningTime: Int,
) : Serializable {
    companion object {
        val posterImages =
            listOf(
                R.drawable.harry_potter_azkaban,
                R.drawable.harry_potter_final_1,
                R.drawable.harry_potter_final_2,
                R.drawable.harry_potter_glass,
                R.drawable.harry_potter_knight,
                R.drawable.harry_potter_prince,
                R.drawable.harry_potter_rock,
                R.drawable.harry_potter_room,
            )

        val values: List<Movie> =
            (1..1000).map { id ->
                val poster = posterImages[(id - 1) % posterImages.size]
                Movie(
                    id = id.toLong(),
                    title = "해리포터 $id",
                    poster = poster,
                    movieDate =
                        MovieDate(
                            LocalDate.of(2025, 4, (id % 28) + 1),
                            LocalDate.of(2025, 5, (id % 28) + 1),
                        ),
                    runningTime = 100 + (id % 60),
                )
            }
    }
}
