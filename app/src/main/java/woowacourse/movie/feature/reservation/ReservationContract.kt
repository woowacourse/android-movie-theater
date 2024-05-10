package woowacourse.movie.feature.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.HeadCount
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface ReservationContract {
    interface View {
        fun showMovieInformation(movie: Movie)

        fun showScreeningDates(screeningDates: List<LocalDate>)

        fun showScreeningTimes(screeningTimes: List<LocalTime>)

        fun changeHeadCount(count: Int)

        fun showResultToast()

        fun navigateToSeatSelection(
            dateTime: LocalDateTime,
            movieId: Int,
            theaterId: Int,
            count: HeadCount,
        )

        fun showDateTime(dateTime: LocalDateTime)

        fun showErrorSnackBar()

        fun showScreeningDate(selectedDateId: Long)

        fun showScreeningTime(selectedTimeId: Long)
    }

    interface Presenter {
        fun loadMovieInformation()

        fun selectScreeningDate(selectedDateId: Long)

        fun selectScreeningTime(selectedTimeId: Long)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun sendTicketToSeatSelection()
    }
}
