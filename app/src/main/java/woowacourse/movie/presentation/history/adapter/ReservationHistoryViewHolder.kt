package woowacourse.movie.presentation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.common.model.TicketUiModel

class ReservationHistoryViewHolder(
    view: ViewGroup,
    eventListener: ReservationHistoryEventListener,
) : BaseViewHolder<TicketUiModel, ItemReservationHistoryBinding>(
    DataBindingUtil.inflate(
        LayoutInflater.from(view.context),
        R.layout.item_reservation_history,
        view,
        false,
    )
) {
    init {
        binding.eventListener = eventListener
    }

    override fun bind(item: TicketUiModel) {
        binding.history = item
    }
}
