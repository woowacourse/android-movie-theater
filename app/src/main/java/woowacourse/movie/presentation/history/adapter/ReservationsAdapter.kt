package woowacourse.movie.presentation.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.common.model.TicketUiModel

class ReservationsAdapter(
    private val eventListener: ReservationHistoryEventListener,
) : ListAdapter<TicketUiModel, BaseViewHolder<TicketUiModel, ItemReservationHistoryBinding>>(
        ReservationDiffUtil,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<TicketUiModel, ItemReservationHistoryBinding> = ReservationHistoryViewHolder(parent, eventListener)

    override fun onBindViewHolder(
        holder: BaseViewHolder<TicketUiModel, ItemReservationHistoryBinding>,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
