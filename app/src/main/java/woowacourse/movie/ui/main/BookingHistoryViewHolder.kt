package woowacourse.movie.ui.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.util.formatBookedDateTime

class BookingHistoryViewHolder(
    private val view: View,
) : RecyclerView.ViewHolder(view) {

    private val dateTime = view.findViewById<TextView>(R.id.textItemBookedDateTime)
    private val movieTitle = view.findViewById<TextView>(R.id.textItemMovieName)
    private lateinit var reservation: ReservationUiModel

    fun bind(reservation: ReservationUiModel) {
        this.reservation = reservation
        dateTime.text = reservation.bookedDateTime.formatBookedDateTime()
        movieTitle.text = reservation.movieTitle
    }

    fun itemClicked(itemClicked: (id: Long) -> Unit) {
        view.setOnClickListener { itemClicked(reservation.id) }
    }
}
