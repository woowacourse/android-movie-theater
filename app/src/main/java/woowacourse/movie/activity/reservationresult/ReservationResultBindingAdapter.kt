package woowacourse.movie.activity.reservationresult

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.view.data.ReservationViewData
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object ReservationResultBindingAdapter {
    @BindingAdapter("reservationDateTime", "format")
    @JvmStatic
    fun formatReservationDateTime(view: TextView, dateTime: LocalDateTime, format: String) {
        val formattedDateTime = DateTimeFormatter.ofPattern(format).format(dateTime)
        view.text = formattedDateTime
    }

    @BindingAdapter("reservatoin", "seatsFormat", "countFormat")
    @JvmStatic
    fun formatReservationCount(
        view: TextView,
        reservation: ReservationViewData,
        seatsFormat: String,
        countFormat: String,
    ) {
        val formattedSeats = reservation.seats.seats.joinToString {
            seatsFormat.format(
                it.rowCharacter,
                it.column + 1,
            )
        }

        val formattedCount = countFormat.format(
            reservation.reservationDetail.peopleCount,
            formattedSeats,
            reservation.reservationDetail.theaterName,
        )

        view.text = formattedCount
    }

    @BindingAdapter("reservationPrice", "priceFormat")
    @JvmStatic
    fun formatReservationPrice(view: TextView, price: Int, priceFormat: String) {
        val formattedPrice =
            priceFormat.format(NumberFormat.getNumberInstance(Locale.US).format(price))
        view.text = formattedPrice
    }
}
