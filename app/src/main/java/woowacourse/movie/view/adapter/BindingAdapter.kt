package woowacourse.movie.view.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.view.mapper.Formatter.localDateToUI
import woowacourse.movie.view.seatSelection.SeatSelectionFormatter.seatsToUI

@BindingAdapter("movieDate")
fun setFormatLocalDate(
    textView: TextView,
    movieDate: MovieDate,
) {
    val formattedStartDate: String = localDateToUI(movieDate.startDate)
    val formattedEndDate: String = localDateToUI(movieDate.endDate)

    textView.text =
        textView.context.getString(
            R.string.movie_screening_date,
            formattedStartDate,
            formattedEndDate,
        )
}

@BindingAdapter("imageRes")
fun setImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}

@BindingAdapter("reserveDate")
fun setFormatReserveDate(
    textView: TextView,
    movieTicket: MovieTicket,
) {
    val formatMovieDate: String = localDateToUI(movieTicket.movieDate)
    val formatMovieTime: String = movieTicket.movieTime.value.toString()

    textView.text =
        textView.context.getString(
            R.string.reservation_complete_ticket_timestamp,
            formatMovieDate,
            formatMovieTime,
        )
}

@BindingAdapter("theaterInfo")
fun setTheaterInfo(
    textView: TextView,
    movieTicket: MovieTicket,
) {
    val formateSeats: String = seatsToUI(movieTicket.seats)

    textView.text =
        textView.context.getString(
            R.string.reservation_complete_seat_theater_name_info,
            movieTicket.seats.size,
            formateSeats,
            movieTicket.theater.name,
        )
}
