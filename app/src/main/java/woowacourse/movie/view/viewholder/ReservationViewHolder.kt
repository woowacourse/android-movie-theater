package woowacourse.movie.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.data.ReservationViewData
import java.time.format.DateTimeFormatter

class ReservationViewHolder(
    view: View,
    onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val dateTime: TextView = view.findViewById(R.id.item_reservation_datetime)
    private val title: TextView = view.findViewById(R.id.item_reservation_title)

    init {
        view.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    fun bind(reservation: ReservationViewData) {
        val dateFormat =
            DateTimeFormatter.ofPattern(dateTime.context.getString(R.string.reservation_item_datetime_format))
        dateTime.text = dateFormat.format(reservation.reservationDetail.date)
        title.text = reservation.movie.title
    }
}
