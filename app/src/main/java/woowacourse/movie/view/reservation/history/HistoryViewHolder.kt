package woowacourse.movie.view.reservation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemHistoryBinding
import woowacourse.movie.domain.model.ReservationInfo

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val onClickHistory: (ReservationInfo) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(reservationInfo: ReservationInfo) {
        binding.tvHistoryTitle.text = reservationInfo.title
        binding.tvHistoryInfo.text =
            "${reservationInfo.reservationDateTime} - ${reservationInfo.cinema.name}"
        binding.root.setOnClickListener(
            { onClickHistory(reservationInfo) },
        )
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickHistory: (ReservationInfo) -> Unit,
        ): HistoryViewHolder {
            val binding =
                ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HistoryViewHolder(binding, onClickHistory)
        }
    }
}
