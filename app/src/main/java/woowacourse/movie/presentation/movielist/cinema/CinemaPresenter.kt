package woowacourse.movie.presentation.movielist.cinema

import woowacourse.movie.data.CinemaData
import woowacourse.movie.domain.model.tools.cinema.Cinema
import woowacourse.movie.domain.model.tools.cinema.MovieTimes
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel

class CinemaPresenter(val view: CinemaContract.View) : CinemaContract.Presenter {

    private val cinemas = CinemaData.Cinemas

    override fun setCinemaModels(movieModel: MovieModel) {
        val cinemaModels = cinemas.mapNotNull { cinema ->
            val movieTimes = cinema.findMovieTimes(movieModel.id)
            getCinemaModels(cinema, movieModel.id, movieTimes)
        }
        view.setCinemaItemAdapter(cinemaModels)
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
