package woowacourse.movie.movieReservation

import woowacourse.movie.common.model.LocalFormattedTime
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.system.StateContainer
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
