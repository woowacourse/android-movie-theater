package woowacourse.movie.presentation.view.reservationlist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.presentation.model.ReservationInfoUiModel

class ReservationViewHolder(
    private val binding: ItemReservationBinding,
    clickListener: ReservationClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clickListener = clickListener
    }

    fun bind(reservationInfoUiModel: ReservationInfoUiModel) {
        binding.reservation = reservationInfoUiModel
    }
}
