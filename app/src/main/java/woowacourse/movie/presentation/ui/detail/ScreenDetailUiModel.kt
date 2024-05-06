package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.presentation.model.Ticket
import java.time.LocalTime

data class ScreenDetailUiModel(
    val screenId: Int,
    val theaterId: Int,
    val screen: Screen,
    val ticket: Ticket = Ticket(Ticket.MIN_TICKET_COUNT),
    val selectableDates: List<ScreenDate> = emptyList(),
    val selectedDate: ScreenDate,
    val selectedTime: LocalTime,
)
