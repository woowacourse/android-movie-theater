package woowacourse.movie.movielist.theaters

import woowacourse.movie.repository.MovieRepository

class TheaterPresenter(
    private val repository: MovieRepository,
    private val view: TheaterContract.View,
) : TheaterContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val theaters = repository.theatersByMovieId(movieId)
        runCatching {
            theaters.map { theater ->
                val screening = repository.screeningByMovieIdAndTheaterId(movieId, theater.id) ?: error("선택한 상영이 존재하지 않습니다")
                screening.theater.toTheaterUiModel(screening.totalScreeningTimesNum())
            }
        }.onSuccess {
            view.showTheaters(it)
        }
    }

    override fun selectTheater(
        movieId: Long,
        theaterId: Long,
    ) {
        runCatching {
            repository.screeningByMovieIdAndTheaterId(movieId, theaterId) ?: error("선택한 상영이 존재하지 않습니다")
        }.onSuccess {
            view.navigateMovieReservation(it.id, theaterId)
        }
    }
}
