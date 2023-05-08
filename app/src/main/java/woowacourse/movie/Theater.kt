package woowacourse.movie

import woowacourse.movie.movie.Movie
import java.io.Serializable

class Theater(
    val name: String,
    val screeningMovies: List<Movie>,
    val screenTimes: Map<Movie, List<String>>
) : Serializable {
    companion object {
        val dummyData = Theater(
            name = "선릉극장",
            screeningMovies = listOf(Movie.dummyData),
            screenTimes = mapOf(Movie.dummyData to listOf("12:00"))
        )
    }
}
