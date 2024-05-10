package woowacourse.movie.screeningmovie.theaters

import woowacourse.movie.repository.MovieRepository
import kotlin.concurrent.thread

class TheaterPresenter(
    private val repository: MovieRepository,
    private val view: TheaterContract.View,
) : TheaterContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        thread {
            val theaters = repository.theatersByMovieId(movieId)
            val uiModels =
                theaters.map { theater ->
                    val screeningMovie = repository.screenMovieById(movieId, theater.id)
                    screeningMovie.theater.toTheaterUiModel(screeningMovie.totalScreeningTimesNum())
                }
            view.showTheaters(uiModels)
        }.join()
    }

    override fun selectTheater(
        movieId: Long,
        theaterId: Long,
    ) {
        thread {
            val screeningMovie = repository.screenMovieById(movieId, theaterId)
            view.navigateMovieReservation(screeningMovie.id, theaterId)
        }.join()
    }
}
