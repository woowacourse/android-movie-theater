package woowacourse.movie.presentation.view.reservationlist

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.presentation.model.ReservationInfoUiModel

object ReservationDiffUtil : DiffUtil.ItemCallback<ReservationInfoUiModel>() {
    override fun areItemsTheSame(
        oldItem: ReservationInfoUiModel,
        newItem: ReservationInfoUiModel,
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: ReservationInfoUiModel,
        newItem: ReservationInfoUiModel,
    ): Boolean = oldItem == newItem
}
