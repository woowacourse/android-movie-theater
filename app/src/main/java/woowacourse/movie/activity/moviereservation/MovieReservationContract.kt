package woowacourse.movie.activity.moviereservation

import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.TheaterMovieViewData
import java.time.LocalDate
import java.time.LocalTime

interface MovieReservationContract {
    interface View {
        fun setUpMovie(theaterMovie: TheaterMovieViewData)
        fun initCount(count: Int)
        fun initDateSpinner(dateIndex: Int, dates: List<LocalFormattedDate>)
        fun initTimeSpinner(timeIndex: Int, times: List<LocalFormattedTime>)
        fun updateCount(count: Int)
        fun navigateToSeatSelection(
            theaterMovie: TheaterMovieViewData,
            reservationDetailViewData: ReservationDetailViewData
        )
    }

    interface Presenter {
        fun setUpMovie(theaterMovie: TheaterMovieViewData)
        fun loadCountData()
        fun loadDateTimeData()
        fun plusCount()
        fun minusCount()
        fun navigateToSeatSelection(
            date: LocalDate,
            time: LocalTime,
            theaterName: String
        )

        fun getCount(): Int
    }
}
