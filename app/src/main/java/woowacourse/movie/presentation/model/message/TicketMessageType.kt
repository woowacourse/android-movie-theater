package woowacourse.movie.presentation.model.message

import android.content.Context
import woowacourse.movie.R

sealed class TicketMessageType : MessageType {
    data class TicketMaxCountMessage(val count: Int) : TicketMessageType() {
        override fun toMessage(context: Context): String = context.getString(R.string.ticke_max_count_message, count)
    }

    data class TicketMinCountMessage(val count: Int) : TicketMessageType() {
        override fun toMessage(context: Context): String = context.getString(R.string.ticke_min_count_message, count)
    }
}
