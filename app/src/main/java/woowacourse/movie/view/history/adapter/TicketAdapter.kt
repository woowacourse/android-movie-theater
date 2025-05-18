package woowacourse.movie.view.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.history.adapter.model.TickRvItem

class TicketAdapter(
    private val handler: Handler,
    private val items: List<TickRvItem>,
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        return when (ViewType.entries[viewType]) {
            ViewType.VIEW_TYPE_TICKET ->
                TicketViewHolder(
                    parent,
                    R.layout.ticket_item,
                    handler,
                )
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
    ) {
        when (holder) {
            is TicketViewHolder -> holder.bind(items[position] as TickRvItem.TicketItem)
        }
    }

    override fun getItemCount() = items.size

    interface Handler : TicketViewHolder.Handler

    enum class ViewType {
        VIEW_TYPE_TICKET,
    }
}
