package woowacourse.movie.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.ReservationUiModel
import java.time.format.DateTimeFormatter

class ReservationViewHolder(itemView: View, val onClickEvent: (ReservationUiModel) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val dateTimeTextView: TextView
    private val titleTextView: TextView

    init {
        dateTimeTextView = itemView.findViewById(R.id.item_reservation_date_time)
        titleTextView = itemView.findViewById(R.id.item_reservation_title)
    }

    fun bind(reservation: ReservationUiModel) {
        itemView.setOnClickListener { onClickEvent(reservation) }
        val dateFormat =
            DateTimeFormatter.ofPattern(titleTextView.context.getString(R.string.reservation_date_time_format))
        dateTimeTextView.text = dateFormat.format(reservation.tickets.list[0].date)
        titleTextView.text = reservation.movie.title
    }
}
