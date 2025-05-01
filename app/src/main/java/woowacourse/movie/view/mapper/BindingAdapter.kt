package woowacourse.movie.view.mapper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.view.mapper.Formatter.localDateToUI
import woowacourse.movie.view.seatSelection.SeatSelectionFormatter.seatsToUI
import java.time.LocalDate

@BindingAdapter("android:startDate", "android:endDate", requireAll = true)
fun setFormatLocalDate(
    textView: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val startDate: String = localDateToUI(startDate)
    val endDate: String = localDateToUI(endDate)

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
    val formatMovieDate: String = localDateToUI(movieDate)
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
    val formateSeats: String = seatsToUI(seats, ", ")

    textView.text =
        textView.context.getString(
            R.string.reservation_complete_seat_theater_name_info,
            seats.size,
            formateSeats,
            theater.name,
        )
}
