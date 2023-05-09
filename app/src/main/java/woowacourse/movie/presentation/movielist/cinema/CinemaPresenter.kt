package woowacourse.movie.presentation.movielist.cinema

import woowacourse.movie.data.cinema.CinemaData
import woowacourse.movie.domain.model.tools.cinema.Cinema
import woowacourse.movie.domain.model.tools.cinema.MovieTimes
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel

class CinemaPresenter(
    val view: CinemaContract.View,
    private val cinemaData: CinemaData,
) : CinemaContract.Presenter {

    override fun setCinemaModels(movieModel: MovieModel) {
        val cinemaModels = cinemaData.getCinemas().mapNotNull { cinema ->
            val movieTimes = cinema.findMovieTimes(movieModel.id)
            getCinemaModels(cinema, movieModel.id, movieTimes)
        }
        view.setCinemaModels(cinemaModels)
    }

    private fun getCinemaModels(
        cinema: Cinema,
        movieId: Long,
        movieTimes: MovieTimes?,
    ): CinemaModel? {
        if (movieTimes == null) return null
        return CinemaModel(cinema.cinemaName, movieId, movieTimes.times)
    }
}
