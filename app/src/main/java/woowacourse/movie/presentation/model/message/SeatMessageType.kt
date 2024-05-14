package woowacourse.movie.presentation.model.message

import android.content.Context
import woowacourse.movie.R

sealed class SeatMessageType : MessageType {
    data class AllSeatsSelectedMessage(val count: Int) : SeatMessageType() {
        override fun toMessage(context: Context): String = context.getString(R.string.all_seats_selected_message, count)
    }
}
