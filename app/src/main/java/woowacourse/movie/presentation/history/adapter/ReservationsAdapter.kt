package woowacourse.movie.presentation.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.common.model.ReservationHistoryUiModel

class ReservationsAdapter(
    private val eventListener: ReservationHistoryEventListener,
) : ListAdapter<ReservationHistoryUiModel, BaseViewHolder<ReservationHistoryUiModel, ItemReservationHistoryBinding>>(
        ReservationDiffUtil,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ReservationHistoryUiModel, ItemReservationHistoryBinding> = ReservationHistoryViewHolder(parent, eventListener)

    override fun onBindViewHolder(
        holder: BaseViewHolder<ReservationHistoryUiModel, ItemReservationHistoryBinding>,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
