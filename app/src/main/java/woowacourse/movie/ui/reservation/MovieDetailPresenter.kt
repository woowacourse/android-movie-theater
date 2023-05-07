package woowacourse.movie.ui.reservation

import com.example.domain.usecase.GetMovieRunningDateUseCase
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain

class MovieDetailPresenter(
    val view: MovieDetailContract.View,
    val cinemaName: String,
    val movie: MovieState
) : MovieDetailContract.Presenter {
    private val getMovieRunningDateUseCase = GetMovieRunningDateUseCase()
    init {
        view.setMovie(movie)
    }

    override var count: CountState = CountState.of(1)
        set(value) {
            field = value
            view.setCountText(value.value)
        }

    override fun plusCount() { count += 1 }

    override fun minusCount() { count -= 1 }

    override fun setUpDateTime() {
        view.initDateTimeSpinner(getMovieRunningDateUseCase(movie.asDomain()), movie.screeningTimes)
    }

    override fun submitReservation() {
        view.navigateToSeatSelectActivity(movie, cinemaName)
    }
}
