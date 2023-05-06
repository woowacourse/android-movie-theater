package woowacourse.movie.presentation.binding.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice

@BindingAdapter("app:pickedSeats")
fun TextView.setPickedSeats(seats: PickedSeats) {
    text = seats.sorted().toString()
}

@BindingAdapter("app:movieDate", "app:movieTime")
fun TextView.setMovieDateTime(date: MovieDate, time: MovieTime) {
    text = context.getString(
        R.string.book_date_time,
        date.year, date.month, date.day, time.hour, time.min
    )
}

@BindingAdapter("app:ticket")
fun TextView.setTicket(ticket: Ticket) {
    text = context.getString(R.string.regular_count, ticket.count)
}

@BindingAdapter("app:ticketPrice", "app:paymentType")
fun TextView.setTicketPrice(ticketPrice: TicketPrice, paymentType: String) {
    text = context.getString(
        R.string.movie_pay_result,
        ticketPrice.amount,
        paymentType,
    )
}
