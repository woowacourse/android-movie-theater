package woowacourse.movie.view.mapper

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.model.Seat

@BindingAdapter("android:seats")
fun setSeats(
    view: TextView,
    seats: List<Seat>,
) {
    val text =
        view.context.getString(
            R.string.seat_split_line,
            seats.joinToString(",") { it.toFormattedRow() + (it.column + 1).toString() },
        )
    view.text = text
}

@BindingAdapter("android:totalPrice")
fun setTotalPrice(
    view: TextView,
    totalPrice: Int,
) {
    val text = view.context.getString(R.string.reservation_total_price, totalPrice)
    view.text = text
}

private fun Seat.toFormattedRow(): String {
    return (this.row + 65).toChar().toString()
}
