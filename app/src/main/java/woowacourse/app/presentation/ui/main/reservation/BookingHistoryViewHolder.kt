package woowacourse.app.presentation.ui.main.reservation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.presentation.model.ReservationUiModel
import woowacourse.app.presentation.util.formatBookedDateTime
import woowacourse.movie.R

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
