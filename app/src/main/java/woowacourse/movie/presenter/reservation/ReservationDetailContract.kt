package woowacourse.movie.presenter.reservation

import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.result.ChangeTicketCountResult

interface ReservationDetailContract {
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
        fun loadMovie(movieId: Int)

        fun loadScreeningPeriod(movieId: Int)

        fun loadScreeningTimes(
            theaterId: Int,
            selectedDate: String,
        )

        fun increaseHeadCount(count: Int)

        fun decreaseHeadCount(count: Int)

        fun initializeReservationButton(dateTime: ScreeningDateTime)

        fun handleHeadCountBounds(result: ChangeTicketCountResult)
    }
}
