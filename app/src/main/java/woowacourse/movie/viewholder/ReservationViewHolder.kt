package woowacourse.movie.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import domain.Reservation
import woowacourse.movie.R
import woowacourse.movie.view.mapper.MovieMapper
import java.time.format.DateTimeFormatter

class ReservationViewHolder(itemView: View, val onClickEvent: (Reservation) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val dateTimeTextView: TextView
    private val titleTextView: TextView

    init {
        dateTimeTextView = itemView.findViewById(R.id.item_reservation_date_time)
        titleTextView = itemView.findViewById(R.id.item_reservation_title)
    }

    fun bind(reservation: Reservation) {
        itemView.setOnClickListener { onClickEvent(reservation) }
        val dateFormat =
            DateTimeFormatter.ofPattern(titleTextView.context.getString(R.string.reservation_date_time_format))
        dateTimeTextView.text = dateFormat.format(reservation.detail.list[0].date)
        titleTextView.text = reservation.movie.title
    }
}
