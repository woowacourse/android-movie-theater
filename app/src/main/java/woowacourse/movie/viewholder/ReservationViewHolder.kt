package woowacourse.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.ReservationItemModel
import woowacourse.movie.databinding.ItemReservationBinding

class ReservationViewHolder(private val binding: ItemReservationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ReservationItemModel) {
        binding.reservations = item
    }
}
