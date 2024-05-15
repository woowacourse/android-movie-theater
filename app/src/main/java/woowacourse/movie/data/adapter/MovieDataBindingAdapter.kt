package woowacourse.movie.data.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.util.Formatter.formatRow

object MovieDataBindingAdapter {
    @BindingAdapter("imgRes")
    @JvmStatic
    fun setImageViewResource(
        imageView: ImageView,
        @DrawableRes
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }

    @BindingAdapter("information")
    @JvmStatic
    fun setResultInformationText(
        textView: TextView,
        movieTicket: MovieTicket,
    ) {
        val seats =
            movieTicket.seats.selectedSeats.joinToString(", ") { seat ->
                textView.context.getString(
                    R.string.seat,
                    seat.row.formatRow(),
                    seat.column.toString(),
                )
            }

        textView.text =
            textView.context.getString(
                R.string.result,
                movieTicket.seats.count,
                seats,
                movieTicket.theaterName,
            )
    }

    @BindingAdapter("information")
    @JvmStatic
    fun setReservationHistoryInformationText(
        textView: TextView,
        reservationHistory: ReservationHistoryEntity,
    ) {
        val theaterName =
            getMovieById(reservationHistory.movieId)?.theaters?.get(reservationHistory.theaterPosition)?.name

        textView.text =
            textView.context.getString(
                R.string.history_information,
                reservationHistory.date,
                reservationHistory.time,
                theaterName,
            )
    }
}
