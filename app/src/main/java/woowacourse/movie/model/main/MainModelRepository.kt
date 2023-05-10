package woowacourse.movie.model.main

import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.movie.Movie
import woowacourse.movie.theater.Theater

interface MainModelRepository {

    fun getMainData(): List<MainData>
    fun getTheaters(): List<TheaterUiModel>
    fun findMovieById(id: Long): Movie
    fun findTheaterById(id: Long): Theater
}
