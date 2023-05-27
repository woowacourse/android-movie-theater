package woowacourse.movie.view.data

import java.time.LocalTime

data class TheaterMovieViewData(
    val theaterName: String = "",
    val movie: MovieViewData,
    val screenTimes: List<LocalTime>
) : java.io.Serializable {
    companion object {
        const val THEATER_MOVIE_EXTRA_NAME = "theaterMovie"
    }
}
