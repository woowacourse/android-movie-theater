package woowacourse.movie.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.formatDotDateTimeColon

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setMovieScreeningDate")
    fun TextView.setMovieScreeningDate(ticket: TicketModel) {
        this.text = ticket.bookedDateTime.formatDotDateTimeColon()
    }

    @JvmStatic
    @BindingAdapter("setMovieTicketCount")
    fun TextView.setMovieTicketCount(ticket: TicketModel) {
        this.text = this.context.getString(R.string.normal_ticket_count_seat).format(
            ticket.count,
            ticket.seats.joinToString(separator = " "),
            ticket.theater
        )
    }

    @JvmStatic
    @BindingAdapter("setMoviePaymentAmount")
    fun TextView.setMoviePaymentAmount(ticket: TicketModel) {
        this.text =
            this.context.getString(R.string.payment_on_site_amount).format(ticket.paymentMoney)
    }
}
