package woowacourse.movie.booking.detail

import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel
import woowacourse.movie.ui.model.TicketUiModel
import java.time.LocalDate
import java.time.LocalTime

interface BookingDetailContract {
    interface View {
        fun showMovieInfo(movie: MovieUiModel)

        fun showScreeningDates(
            dates: List<LocalDate>,
            selected: LocalDate,
        )

        fun showScreeningTimes(
            times: List<LocalTime>,
            selected: LocalTime,
        )

        fun startSeatSelectionActivity(ticket: TicketUiModel)

        fun showHeadCount(headCount: Int)
    }

    interface Presenter {
        fun initializeData(
            movie: MovieUiModel,
            theater: TheaterUiModel,
        )

        fun setUpTicket()

        fun createDefaultTicket()

        fun restoreTicketData(
            headCount: Int,
            screeningDate: String?,
            screeningTime: String?,
        )

        fun selectDate(date: LocalDate)

        fun selectTime(time: LocalTime)

        fun increaseHeadCount()

        fun decreaseHeadCount()

        fun confirmReservation()
    }
}
