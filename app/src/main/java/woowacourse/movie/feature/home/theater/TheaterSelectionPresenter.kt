package woowacourse.movie.feature.home.theater

import woowacourse.movie.data.movie.MovieRepositoryImpl

class TheaterSelectionPresenter(private val view: TheaterSelectionContract.View) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        runCatching { MovieRepositoryImpl.getMovieById(movieId) }
            .onSuccess {
                view.setUpTheaterAdapter(it.theaters)
            }
            .onFailure {
                view.showToastInvalidMovieIdError(it)
            }
    }
}
