package woowacourse.movie.ui.reservationhistory.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.db.ReservationHistory

class ReservationHistoryDiffUtil : DiffUtil.ItemCallback<ReservationHistory>() {
    override fun areItemsTheSame(
        oldItem: ReservationHistory,
        newItem: ReservationHistory,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ReservationHistory,
        newItem: ReservationHistory,
    ): Boolean {
        return oldItem == newItem
    }
}
