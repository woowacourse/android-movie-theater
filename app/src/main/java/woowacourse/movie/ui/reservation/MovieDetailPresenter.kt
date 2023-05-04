package woowacourse.movie.ui.reservation

import com.example.domain.usecase.GetMovieRunningDateUseCase
import com.example.domain.usecase.GetMovieRunningTimeUseCase
import java.time.LocalDate
import java.time.LocalTime
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.mapper.asDomain

class MovieDetailPresenter(
    val view: MovieDetailContract.View
) : MovieDetailContract.Presenter {
    private val getMovieRunningDateUseCase = GetMovieRunningDateUseCase()
    private val getMovieRunningTimeUseCase = GetMovieRunningTimeUseCase()

    private lateinit var movie: MovieState
    private lateinit var cinemaName: String

    override var count: CountState = CountState.of(1)
        set(value) {
            field = value
            view.setCounterText(value.value)
        }

    override fun init(cinemaName: String, movie: MovieState) {
        this.cinemaName = cinemaName
        this.movie = movie
        view.setBinding(movie)
    }
    override fun onPlusClick() { count += 1 }

    override fun onMinusClick() { count -= 1 }

    override fun getMovieRunningDates(): List<LocalDate> {
        return getMovieRunningDateUseCase(movie.asDomain())
    }

    override fun getMovieRunningTimes(): List<LocalTime> {
        return movie.screeningTimes
    }

    override fun onReserveButtonClick() {
        view.navigateSeatSelectActivity(movie, cinemaName)
    }
}
