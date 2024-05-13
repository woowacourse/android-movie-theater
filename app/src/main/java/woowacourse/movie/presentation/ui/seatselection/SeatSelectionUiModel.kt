package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.UserSeat
import java.time.LocalDateTime

data class SeatSelectionUiModel(
    val theaterName: String = "",
    val screen: Screen? = null,
    val dateTime: LocalDateTime? = null,
    val ticketQuantity: Int = 0,
    val userSeat: UserSeat = UserSeat(emptyList()),
    val totalPrice: Int = 0,
) {
    fun copyWithReservationInfo(reservationInfo: ReservationInfo): SeatSelectionUiModel =
        this.copy(
            dateTime = reservationInfo.dateTime,
            ticketQuantity = reservationInfo.ticketQuantity,
        )

    fun createReservation(
        screen: Screen,
        dateTime: LocalDateTime,
    ) = Reservation(
        id = 0L,
        theaterName = this.theaterName,
        movieTitle = screen.movie.title,
        ticketCount = this.ticketQuantity,
        seats =
            this.userSeat.seatModels.filter { it.isSelected }
                .map { Seat(it.column, it.row, it.seatRank) },
        dateTime = dateTime,
    )
}
