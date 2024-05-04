package woowacourse.movie.feature.home.theater

import woowacourse.movie.data.MovieRepository

class TheaterSelectionPresenter(private val view: TheaterSelectionContract.View) :
    TheaterSelectionContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val movie =
            runCatching {
                MovieRepository.getMovieById(movieId)
            }.getOrElse {
                view.handleInvalidMovieIdError(it)
                return
            }

        view.setUpTheaterAdapter(movie.theaters)
    }
}
