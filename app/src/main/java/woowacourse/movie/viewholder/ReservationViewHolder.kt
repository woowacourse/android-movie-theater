package woowacourse.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.fragment.reservationlist.ReservationItemModel

class ReservationViewHolder(private val binding: ItemReservationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ReservationItemModel) {
        binding.reservations = item
    }
}
