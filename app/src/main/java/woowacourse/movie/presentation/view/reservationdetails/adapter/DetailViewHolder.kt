package woowacourse.movie.presentation.view.reservationdetails.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationDetailBinding
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservationdetails.ReservationDetailsContract

class DetailViewHolder(private val binding: ItemReservationDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        reservationDetail: MovieTicketUiModel,
        itemClickListener: ReservationDetailsContract.ViewActions,
    ) {
        binding.reservationDetail = reservationDetail
        binding.listener = itemClickListener
    }
}
