package woowacourse.movie.movielist.theaters

import woowacourse.movie.usecase.FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.usecase.FetchTheatersWithMovieIdUseCase
import woowacourse.movie.util.runOnOtherThreadAndReturn

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val fetchTheatersWithMovieIdUseCase: FetchTheatersWithMovieIdUseCase,
    private val fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase: FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase,
) : TheaterContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val result = runOnOtherThreadAndReturn { fetchTheatersWithMovieIdUseCase(movieId) }
        result.map { theaters ->
            theaters.map { theater ->
                val timeCount = getScheduleTimeCount(movieId, theater.id)
                TheaterUiModel(theater.id, theater.name, timeCount.toString())
            }
        }.onSuccess {
            view.showTheaters(it)
        }.onFailure {
            // view.showError(it)
            error(it)
        }
    }

    private fun getScheduleTimeCount(
        movieId: Long,
        theaterId: Long,
    ) = runOnOtherThreadAndReturn {
        fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(
            movieId,
            theaterId,
        ).map { it.totalScreeningTimesNum() }
    }.getOrThrow()

    override fun selectTheater(
        movieId: Long,
        theaterId: Long,
    ) {
        view.navigateToMovieDetail(movieId, theaterId)
    }
}
