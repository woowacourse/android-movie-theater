package woowacourse.movie.view.main.reservationlist.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.model.reservation.ReservationInfo

object ReservationDataDiffUtil : DiffUtil.ItemCallback<ReservationInfo>() {
    override fun areItemsTheSame(
        oldItem: ReservationInfo,
        newItem: ReservationInfo,
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: ReservationInfo,
        newItem: ReservationInfo,
    ): Boolean = oldItem == newItem
}
