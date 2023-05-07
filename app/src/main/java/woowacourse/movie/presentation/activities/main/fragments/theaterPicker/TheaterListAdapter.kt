package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.theaterPicker.viewholder.TheaterViewHolder
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.item.ListItem

class TheaterListAdapter(
    onItemClick: (ListItem) -> Unit = {},
) : BaseRecyclerView.Adapter(onItemClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerView.BaseViewHolder {
        return TheaterViewHolder(parent.inflate(R.layout.item_theater), onItemViewClick)
    }

    fun appendAll(newItems: List<ListItem>) {
        items.addAll(newItems)
        notifyItemRangeChanged(items.size, newItems.size)
    }
}
