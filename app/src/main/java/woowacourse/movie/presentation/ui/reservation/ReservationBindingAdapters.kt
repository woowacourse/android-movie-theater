package woowacourse.movie.presentation.ui.reservation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.presentation.utils.currency
import woowacourse.movie.presentation.utils.toScreeningDate
import java.time.LocalDateTime

@BindingAdapter("count", "seats", "theaterName")
fun TextView.showReservation(
    count: Int,
    seats: List<Seat>,
    theaterName: String,
) {
    this.text =
        this.context.getString(R.string.reserve_count, count, seats.toSeatString(), theaterName)
}

fun List<Seat>.toSeatString(): String = this.map { "${it.column}${it.row + 1}" }.sorted().joinToString(", ")

// this.joinToString(", ")
@BindingAdapter("bindShowTotalPrice")
fun TextView.showTotalPrice(totalPrice: Int) {
    this.text =
        this.context.getString(R.string.reserve_amount, totalPrice.currency(this.context))
}

@BindingAdapter("bindShowReservationDate")
fun TextView.showReservationDate(dateTime: LocalDateTime) {
    this.text = dateTime.toScreeningDate()
}
