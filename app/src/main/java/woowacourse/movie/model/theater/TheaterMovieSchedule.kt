package woowacourse.movie.model.theater

import woowacourse.movie.model.movie.Movie
import java.io.Serializable

data class TheaterMovieSchedule(
    val theater: Theater,
    val movie: Movie,
    val screeningInfo: ScreeningInfo,
) : Serializable
