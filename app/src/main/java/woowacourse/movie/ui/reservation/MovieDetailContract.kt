package woowacourse.movie.ui.reservation

import java.time.LocalDate
import java.time.LocalTime
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState

interface MovieDetailContract {
    interface View {
        var presenter: Presenter
        fun setCounterText(count: Int)
        fun setMovie(movie: MovieState)
        fun navigateSeatSelectActivity(movie: MovieState, cinemaName: String)
    }

    interface Presenter {
        var count: CountState
        fun init(cinemaName: String, movie: MovieState)
        fun onPlusClick()
        fun onMinusClick()
        fun onReserveButtonClick()
        fun getMovieRunningDates(): List<LocalDate>
        fun getMovieRunningTimes(): List<LocalTime>
    }
}
