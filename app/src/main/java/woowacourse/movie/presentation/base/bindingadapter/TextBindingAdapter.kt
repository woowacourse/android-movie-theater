package woowacourse.movie.presentation.base.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.presentation.ReservationUiFormatter
import woowacourse.movie.presentation.model.SeatsUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import java.time.LocalDateTime

@BindingAdapter("theaterNameText")
fun setTheaterNameText(
    view: TextView,
    theater: TheaterUiModel?,
) {
    theater ?: return
    val theaterName = theater.name
    view.text = view.context.getString(R.string.bottom_sheet_dialog_theater_name, theaterName)
}

@BindingAdapter("timeSlotText")
fun setTimeSlotText(
    view: TextView,
    theater: TheaterUiModel?,
) {
    theater ?: return
    val count = theater.totalScreeningTimes
    view.text = view.context.getString(R.string.bottom_sheet_dialog_time_slot, count)
}

@BindingAdapter("totalPrice")
fun setTotalPrice(
    view: TextView,
    totalPrice: Int,
) {
    val context = view.context
    val formatted =
        context
            .getString(R.string.seat_select_ticket_price)
            .format(ReservationUiFormatter.priceToUI(totalPrice))
    view.text = formatted
}

@BindingAdapter("dateTime")
fun setDateTime(
    view: TextView,
    dateTime: LocalDateTime,
) {
    val context = view.context
    val date = dateTime.toLocalDate()
    val time = dateTime.toLocalTime()

    val formatted =
        context.getString(
            R.string.reservation_complete_date_time,
            ReservationUiFormatter.localDateToUI(date),
            time,
        )
    view.text = formatted
}

@BindingAdapter(value = ["seats", "theaterName"])
fun setSeatInfo(
    view: TextView,
    seats: SeatsUiModel,
    theaterName: String,
) {
    val context = view.context
    val seatCount = seats.size
    val seatLabels = seats.labels().sorted().joinToString()

    val formatted =
        context.getString(
            R.string.reservation_complete_ticket_count,
            seatCount,
            seatLabels,
            theaterName,
        )
    view.text = formatted
}
