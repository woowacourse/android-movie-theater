package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.Ticket.Companion.MIN_TICKET_COUNT
import java.time.LocalTime

data class DetailUiModel(
    val screenId: Int,
    val theaterId: Int,
    val movieId: Int,
    val screen: Screen,
    val ticket: Ticket = Ticket(MIN_TICKET_COUNT),
    val selectableDates: List<ScreenDate>,
    val selectedDate: ScreenDate,
    val selectedTime: LocalTime,
)
