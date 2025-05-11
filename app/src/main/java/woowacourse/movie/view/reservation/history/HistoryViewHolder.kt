package woowacourse.movie.view.reservation.history

import android.view.View
import android.view.View.OnClickListener
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
            reservationInfo.reservationDateTime.toString() + reservationInfo.cinema.name
        binding.root.setOnClickListener(
            object : OnClickListener {
                override fun onClick(v: View?) {
                    onClickHistory(reservationInfo)
                }
            },
        )
    }
}
