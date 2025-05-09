package woowacourse.movie.view.history.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.TicketItemBinding
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.history.adapter.model.TickRvItem

class TicketViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val handler: Handler,
) : BaseViewHolder(parent, layoutId) {
    fun bind(item: TickRvItem.TicketItem) {
        TicketItemBinding.bind(itemView).apply {
            model = item
            eventListener = handler
        }
    }

    interface Handler {
        fun onClick(ticket: Long)
    }
}
