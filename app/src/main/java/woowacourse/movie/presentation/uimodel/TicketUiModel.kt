package woowacourse.movie.presentation.uimodel

import android.content.Context
import woowacourse.movie.R

data class TicketUiModel(
    val ticketId: Long,
    val title: String,
    val startTime: String,
    val screeningDate: String,
    val theaterName: String,
) {
    fun reservationSchedule(context: Context): String {
        return if (theaterName.isNotEmpty()) {
            context.getString(
                R.string.reservation_schedule_format,
                screeningDate,
                startTime,
                theaterName,
            )
        } else {
            ""
        }
    }
}
