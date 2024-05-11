package woowacourse.movie.reservationlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.reservationlist.uimodel.ReservationUiModel

class ReservationViewHolder(
    val binding: ItemReservationBinding,
    val clickListener: AdapterClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ReservationUiModel) {
        binding.reservation = item
        binding.clickListener = clickListener
    }
}
