package woowacourse.movie.ui.reservation

import java.time.LocalDate
import java.time.LocalTime
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState

interface MovieDetailContract {
    interface View {
        var presenter: Presenter
        fun setCounterText(count: Int)
    }

    interface Presenter {
        var count: CountState
        fun plus()
        fun minus()
        fun getMovieRunningDates(movie: MovieState): List<LocalDate>
        fun getMovieRunningTimes(date: LocalDate): List<LocalTime>
    }
}
