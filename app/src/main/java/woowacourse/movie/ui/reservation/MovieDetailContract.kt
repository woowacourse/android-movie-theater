package woowacourse.movie.ui.reservation

import java.time.LocalDate
import java.time.LocalTime
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.BaseContract

interface MovieDetailContract {
    interface View {
        fun initDateTimeSpinner(dates: List<LocalDate>, times: List<LocalTime>)
        fun showCountText(count: Int)
        fun showMovie(movie: MovieState)
        fun navigateToSeatSelectActivity(movie: MovieState, cinemaName: String)
    }

    interface Presenter : BaseContract.Presenter {
        var count: CountState
        fun plusCount()
        fun minusCount()
        fun submitReservation()
        fun setUpDateTime()
    }
}
