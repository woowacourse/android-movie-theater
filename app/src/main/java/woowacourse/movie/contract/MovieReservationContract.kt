package woowacourse.movie.contract

import android.os.Bundle
import woowacourse.movie.data.LocalFormattedTime
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.ReservationDetailViewData
import java.time.LocalDate
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View {
        val presenter: Presenter
        fun setMovieData(movie: MovieViewData)
        fun setCounterText(count: Int)
        fun setTimeSpinner(times: List<LocalFormattedTime>)
        fun startReservationResultActivity(reservationDetail: ReservationDetailViewData, movie: MovieViewData)
    }

    interface Presenter {
        val view: View
        fun addPeopleCount(count: Int)
        fun minusPeopleCount(count: Int)
        fun reserveMovie(date: LocalDateTime, movie: MovieViewData)
        fun selectDate(date: LocalDate)
        fun save(outState: Bundle)
        fun load(savedInstanceState: Bundle?)
    }
}
