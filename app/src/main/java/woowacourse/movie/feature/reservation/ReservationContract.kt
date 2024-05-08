package woowacourse.movie.feature.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.ticket.HeadCount
import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        fun showMovieInformation(movie: Movie)

        fun showScreeningDates(screeningDates: List<LocalDate>)

        fun showScreeningTimes(screeningTimes: List<LocalTime>)

        fun changeHeadCount(count: Int)

        fun showResultToast()

        fun navigateToSeatSelection(
            dateTime: ScreeningDateTime,
            movieId: Int,
            theaterId: Int,
            count: HeadCount,
        )

        fun showDateTime(dateTime: ScreeningDateTime)

        fun showErrorSnackBar()

        fun showScreeningDate(selectedDateId: Long)

        fun showScreeningTime(selectedTimeId: Long)
    }

    interface Presenter {
        fun loadMovie()

        fun loadScreeningDates()

        fun loadScreeningTimes()

        fun selectScreeningDate(selectedDateId: Long)

        fun selectScreeningTime(selectedTimeId: Long)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun sendTicketToSeatSelection()

        fun handleUndeliveredData()
    }
}
