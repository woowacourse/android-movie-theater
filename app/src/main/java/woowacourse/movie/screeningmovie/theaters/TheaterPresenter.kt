package woowacourse.movie.screeningmovie.theaters

import woowacourse.movie.repository.MovieRepository

class TheaterPresenter(
    private val repository: MovieRepository,
    private val view: TheaterContract.View
): TheaterContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        runCatching {
            repository.screenMovieById(movieId)
        }.onSuccess { screeningMovie ->
            /*val theaters = screeningMovie.theaters
            view.showTheaters(th)*/
        }
    }

    override fun selectTheater() {
        TODO("Not yet implemented")
    }
}
