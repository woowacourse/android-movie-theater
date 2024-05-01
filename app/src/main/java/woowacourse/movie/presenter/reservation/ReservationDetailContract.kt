package woowacourse.movie.presenter.reservation

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.result.ChangeTicketCountResult

interface ReservationDetailContract {
    interface View {
        fun showMovieInformation(movie: Movie)

        fun showScreeningPeriod(movie: Movie)

        fun showScreeningTimes(
            movie: Movie,
            selectedDate: String,
        )

        fun changeHeadCount(count: Int)

        fun showResultToast()

        fun navigateToSeatSelection(
            movieId: Int,
            dateTime: ScreeningDateTime,
            count: HeadCount,
        )

        fun showErrorToast()
    }

    interface Presenter {
        fun loadMovie(movieId: Int)

        fun loadScreeningPeriod(movieId: Int)

        fun loadScreeningTimes(
            movieId: Int,
            selectedDate: String,
        )

        fun increaseHeadCount(count: Int)

        fun decreaseHeadCount(count: Int)

        fun initializeReservationButton(
            movieId: Int,
            dateTime: ScreeningDateTime,
        )

        fun handleHeadCountBounds(result: ChangeTicketCountResult)
    }
}
