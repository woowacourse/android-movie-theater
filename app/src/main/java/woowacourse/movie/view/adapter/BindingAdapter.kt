package woowacourse.movie.view.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.view.mapper.Formatter.localDateToUi
import woowacourse.movie.view.mapper.Formatter.priceToUi
import woowacourse.movie.view.seatSelection.SeatSelectionFormatter.seatsToUi
import java.time.LocalDate

@BindingAdapter("android:startDate", "android:endDate", requireAll = true)
fun setFormatLocalDate(
    textView: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val startDate: String = localDateToUi(startDate)
    val endDate: String = localDateToUi(endDate)

    textView.text = textView.context.getString(R.string.movie_screening_date, startDate, endDate)
}

@BindingAdapter("android:imageRes")
fun setImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}

@BindingAdapter("android:movieDate", "android:movieTime", requireAll = true)
fun setFormatLocalDateTime(
    textView: TextView,
    movieDate: LocalDate,
    movieTime: MovieTime,
) {
    val formatMovieDate: String = localDateToUi(movieDate)
    val formatMovieTime: String = movieTime.value.toString()

    textView.text =
        textView.context.getString(
            R.string.reservation_complete_ticket_timestamp,
            formatMovieDate,
            formatMovieTime,
        )
}

@BindingAdapter("android:seats", "android:theater", requireAll = true)
fun setTheaterInfo(
    textView: TextView,
    seats: List<Seat>,
    theater: Theater,
) {
    val formateSeats: String = seatsToUi(seats, ", ")

    textView.text =
        textView.context.getString(
            R.string.reservation_complete_seat_theater_name_info,
            seats.size,
            formateSeats,
            theater.name,
        )
}

@BindingAdapter("android:ticketPrice", "android:isPayed", requireAll = true)
fun setFormatTicketPrice(
    textView: TextView,
    ticketPrice: Int,
    isPayed: Boolean,
) {
    val formatPrice: String = priceToUi(ticketPrice)

    if (isPayed) {
        textView.text =
            textView.context.getString(
                R.string.reservation_complete_ticket_price,
                formatPrice,
            )
    } else {
        textView.text =
            textView.context.getString(
                R.string.seat_selection_ticket_price,
                formatPrice,
            )
    }
}
