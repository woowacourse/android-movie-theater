package woowacourse.movie.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.databinding.ItemReservationBinding
import java.time.format.DateTimeFormatter

class ReservationViewHolder(
    private val binding: ItemReservationBinding,
    onClickItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(reservation: ReservationViewData) {
        val dateFormat =
            DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.reservation_item_datetime_format))
        binding.itemReservationDatetime.text = dateFormat.format(reservation.reservationDetail.date)
        binding.itemReservationTitle.text = reservation.movie.title
    }
}
