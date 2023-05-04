package woowacourse.movie.data

import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterState

interface TheaterRepository {
    fun getAllTheaters(): List<TheaterState> // 모든 극장의 상영 정보를

    fun getScreeningMovieTheaters(movieState: MovieState): List<TheaterState> // 해당 영화를 상영하는 극장의 정보들만
}
