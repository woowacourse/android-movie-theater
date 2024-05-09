package woowacourse.movie.presentation.homefragments.reservationList

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.presentation.homefragments.reservationList.uiModel.ReservationItemUiModel

class ReservationListViewHolder(
    private val binding: ItemReservationBinding,
    val listener: ReservationListClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(reservationItemUiModel: ReservationItemUiModel) {
        binding.root.post {
            binding.reservationItem = reservationItemUiModel
            binding.listener = listener
        }
    }
}
