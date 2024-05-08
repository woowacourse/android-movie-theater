package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.presentation.model.UserSeat
import java.time.LocalDateTime

data class SeatSelectionUiModel(
    val theaterId: Int,
    val screen: Screen,
    val dateTime: LocalDateTime,
    val ticketCount: Int,
    val userSeat: UserSeat = UserSeat(emptyList()),
    val totalPrice: Int = 0,
)
