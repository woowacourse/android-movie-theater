package woowacourse.movie.presentation.views.main.fragments.history.recyclerview

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.ListItem

class HistoryListAdapter(
    onItemClick: (ListItem) -> Unit = {},
) : BaseRecyclerView.Adapter<ListItem>(onItemClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerView.BaseViewHolder {
        return HistoryViewHolder(parent.inflate(R.layout.item_history), onItemViewClick)
    }

    fun appendAll(newItems: List<ListItem>) {
        items.addAll(newItems)
        notifyItemRangeChanged(items.size, newItems.size)
    }

    fun append(newItem: ListItem) {
        items.add(newItem)
        notifyItemInserted(items.size - 1)
    }
}
