package woowacourse.movie.feature.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.ticket.HeadCount

interface ReservationContract {
    interface View {
        fun showMovieInformation(movie: Movie)

        fun showScreeningPeriod(movie: Movie)

        fun showScreeningTimes(
            screeningTimes: ScreeningTimes,
            selectedDate: String,
        )

        fun changeHeadCount(count: Int)

        fun showResultToast()

        fun navigateToSeatSelection(
            dateTime: ScreeningDateTime,
            count: HeadCount,
        )

        fun showErrorToast()
    }

    interface Presenter {
        fun loadMovie()

        fun loadScreeningPeriod()

        fun loadScreeningTimes(selectedDate: String)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun sendTicketToSeatSelection(dateTime: ScreeningDateTime)

        fun handleHeadCountBounds(result: ChangeTicketCountResult)

        fun restoreHeadCount()
    }
}
