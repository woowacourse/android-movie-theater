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

    override var count: CountState = CountState.of(1)

    override fun plus() {
        count += 1
        view.setCounterText(count.value)
    }

    override fun minus() {
        count -= 1
        view.setCounterText(count.value)
    }

    override fun getMovieRunningDates(movie: MovieState): List<LocalDate> {
        return getMovieRunningDateUseCase(movie.asDomain())
    }

    override fun getMovieRunningTimes(movie: MovieState): List<LocalTime> {
        return movie.screeningTimes
    }
}
