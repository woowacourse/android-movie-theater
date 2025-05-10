package woowacourse.movie.view.main.reservationlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservedMovieBinding
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.view.util.ReservationUiFormatter

class ReservationListViewHolder(
    private val binding: ItemReservedMovieBinding,
    private val clickListener: ReservationListClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(reservationInfo: ReservationInfo) {
        binding.itemReservedMovieTitle.text = reservationInfo.title
        binding.itemReservedMovieDate.text =
            ReservationUiFormatter.localDateToUI(reservationInfo.date)
        binding.itemReservedMovieTime.text = reservationInfo.time
        binding.itemReservedMovieTheaterName.text = reservationInfo.theaterName
        binding.root.setOnClickListener { clickListener.onReservationInfoClick(reservationInfo) }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: ReservationListClickListener,
        ): ReservationListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemReservedMovieBinding.inflate(layoutInflater, parent, false)
            return ReservationListViewHolder(binding, clickListener)
        }
    }
}
