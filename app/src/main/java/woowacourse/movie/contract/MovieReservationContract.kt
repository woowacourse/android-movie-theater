package woowacourse.movie.contract

import woowacourse.movie.data.LocalFormattedTime
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.system.StateContainer
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View {
        val presenter: Presenter
        fun setMovieData(movie: MovieViewData)
        fun setCounterText(count: Int)
        fun setTimeSpinner(times: List<LocalFormattedTime>)
        fun saveTimeSpinner(outState: StateContainer)
        fun startReservationResultActivity(reservationDetail: ReservationDetailViewData, movie: MovieViewData, theaterName: String)
    }

    interface Presenter {
        val view: View
        fun addPeopleCount(count: Int)
        fun minusPeopleCount(count: Int)
        fun reserveMovie(date: LocalDateTime, movie: MovieViewData, theaterName: String)
        fun selectDate()
        fun save(outState: StateContainer)
        fun load(savedInstanceState: StateContainer?)
    }
}
