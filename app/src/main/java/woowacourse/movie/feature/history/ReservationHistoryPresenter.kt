package woowacourse.movie.feature.history

import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket
import java.time.LocalDate
import java.time.LocalTime

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun loadTicket() {
        // TODO 임시값 Room 데이터로 변경 후 제거
        val temporaryTickets =
            listOf(
                Ticket(0, "잠실 극장", Seats(), ScreeningDateTime(LocalDate.of(2024, 3, 2), LocalTime.of(11, 0)), 0),
                Ticket(1, "선릉 극장", Seats(), ScreeningDateTime(LocalDate.of(2024, 3, 5), LocalTime.of(6, 13)), 0),
                Ticket(2, "강남 극장", Seats(), ScreeningDateTime(LocalDate.of(2024, 3, 8), LocalTime.of(2, 30)), 0),
            )
        view.showReservationHistory(temporaryTickets)
    }
}
