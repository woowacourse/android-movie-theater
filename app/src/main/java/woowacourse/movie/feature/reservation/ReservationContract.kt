package woowacourse.movie.feature.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.ticket.HeadCount
import java.time.LocalTime

interface ReservationContract {
    interface View {
        fun showMovieInformation(movie: Movie)

        fun showScreeningPeriod(movie: Movie)

        fun showScreeningTimes(
            screeningTimes: List<LocalTime>,
            selectedDate: String,
        )

        fun changeHeadCount(count: Int)

        fun showResultToast()

        fun navigateToSeatSelection(
            dateTime: ScreeningDateTime,
            movieId: Int,
            theaterId: Int,
            count: HeadCount,
        )

        fun getScreeningDate(): String

        fun getScreeningTime(): String

        fun showDateTime(dateTime: ScreeningDateTime)

        fun showErrorSnackBar()
    }

    interface Presenter {
        fun loadMovie()

        fun loadScreeningPeriod()

        fun loadScreeningTimes(selectedDate: String)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun sendTicketToSeatSelection()

        fun handleHeadCountBounds(result: ChangeTicketCountResult)

        fun restoreHeadCount()

        fun handleUndeliveredData()
    }
}
