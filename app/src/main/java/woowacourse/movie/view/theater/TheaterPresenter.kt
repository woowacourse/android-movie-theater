package woowacourse.movie.view.theater

import woowacourse.movie.domain.model.MovieDao
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TheaterUIModel
import woowacourse.movie.domain.model.toUiModel
import woowacourse.movie.view.model.MovieUiModel

class TheaterPresenter(
    val view: TheaterContract.View,
    private val movie: MovieUiModel,
) : TheaterContract.Presenter {
    private val movieDao: MovieDao by lazy { MovieDao() }

    override fun fetchTheaters() {
        val theaterNames = movieDao.getTheaterNames()
        val theaters =
            theaterNames.map { name ->
                val movies = movieDao.getMovies(name)
                Theater(name, movies)
            }
        view.showTheaters(theaters.map { it.toUiModel(movie) })
    }

    override fun theaterSelected(theaterUIModel: TheaterUIModel) {
        view.navigateToReservation(theaterUIModel)
    }
}
