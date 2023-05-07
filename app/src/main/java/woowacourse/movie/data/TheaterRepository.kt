package woowacourse.movie.data

import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterScreeningState

interface TheaterRepository {
    fun getAllTheaters(): List<TheaterScreeningState> // 모든 극장의 상영 정보를

    fun getScreeningMovieTheaters(movieState: MovieState): List<TheaterScreeningState> // 해당 영화를 상영하는 극장의 정보들만
}
