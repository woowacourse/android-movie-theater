package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.presentation.model.UserSeat
import java.time.LocalDateTime

data class SeatSelectionUiModel(
    val theaterName: String = "",
    val screen: Screen? = null,
    val dateTime: LocalDateTime? = null,
    val ticketQuantity: Int = 0,
    val userSeat: UserSeat = UserSeat(emptyList()),
    val totalPrice: Int = 0,
)
