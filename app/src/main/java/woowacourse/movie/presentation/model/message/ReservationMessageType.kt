package woowacourse.movie.presentation.model.message

import android.content.Context
import woowacourse.movie.R

sealed class ReservationMessageType : MessageType {
    data object ReservationSuccessMessage : ReservationMessageType() {
        override fun toMessage(context: Context): String = context.getString(R.string.reservation_success_message)
    }
}
