package woowacourse.movie.ui.reservation

import com.example.domain.usecase.GetMovieRunningDateUseCase
import com.example.domain.usecase.GetMovieRunningTimeUseCase
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain

class MovieDetailPresenter(
    val view: MovieDetailContract.View,
    val cinemaName: String,
    val movie: MovieState
) : MovieDetailContract.Presenter {
    private val getMovieRunningDateUseCase = GetMovieRunningDateUseCase()
    private val getMovieRunningTimeUseCase = GetMovieRunningTimeUseCase()

    init {
        view.setMovie(movie)
    }

    override var count: CountState = CountState.of(1)
        set(value) {
            field = value
            view.setCounterText(value.value)
        }

    override fun onPlusClick() { count += 1 }

    override fun onMinusClick() { count -= 1 }

    override fun getMovieRunningDateTimes() {
        view.setDateTimeSpinner(getMovieRunningDateUseCase(movie.asDomain()), movie.screeningTimes)
    }

    override fun onReserveButtonClick() {
        view.navigateSeatSelectActivity(movie, cinemaName)
    }
}
