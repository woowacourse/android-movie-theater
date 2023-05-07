package woowacourse.movie.view.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.model.ReservationUiModel
import java.time.format.DateTimeFormatter

class ReservationViewHolder(private val binding: ItemReservationBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(reservation: ReservationUiModel, onClickEvent: (ReservationUiModel) -> Unit) {
        itemView.setOnClickListener { onClickEvent(reservation) }
        val dateFormat =
            DateTimeFormatter.ofPattern(binding.itemReservationDateTime.context.getString(R.string.reservation_date_time_format))
        binding.itemReservationDateTime.text = dateFormat.format(reservation.tickets.list[0].date)
        binding.itemReservationTitle.text = reservation.movie.title
    }
}
