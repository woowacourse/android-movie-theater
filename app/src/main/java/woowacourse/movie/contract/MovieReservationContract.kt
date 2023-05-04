package woowacourse.movie.contract

import woowacourse.movie.data.LocalFormattedTime
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.system.StateContainer
import java.time.LocalDate
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View {
        val presenter: Presenter
        fun setMovieData(movie: MovieViewData)
        fun setCounterText(count: Int)
        fun setTimeSpinner(times: List<LocalFormattedTime>)
        fun saveTimeSpinner(outState: StateContainer)
        fun startReservationResultActivity(reservationDetail: ReservationDetailViewData, movie: MovieViewData)
    }

    interface Presenter {
        val view: View
        fun initActivity(movie: MovieViewData)
        fun addPeopleCount(count: Int)
        fun minusPeopleCount(count: Int)
        fun reserveMovie(date: LocalDateTime, movie: MovieViewData)
        fun selectDate(date: LocalDate)
        fun save(outState: StateContainer)
        fun load(savedInstanceState: StateContainer?)
    }
}
