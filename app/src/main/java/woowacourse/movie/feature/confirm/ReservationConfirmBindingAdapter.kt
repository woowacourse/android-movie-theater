package woowacourse.movie.feature.confirm

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState

@BindingAdapter("reservationCountAndSeats")
fun setReservationCountAndSeats(view: TextView, tickets: TicketsState) {
    view.text = view.context.getString(
        R.string.person_count_and_seat_theater,
        tickets.tickets.size,
        tickets.tickets.map { it.seatPositionState }.joinToString { it.toString() },
        tickets.theater.theaterName
    )
}
