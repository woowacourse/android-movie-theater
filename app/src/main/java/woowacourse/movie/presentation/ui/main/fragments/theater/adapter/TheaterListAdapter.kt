package woowacourse.movie.presentation.ui.main.fragments.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.ui.main.fragments.theater.adapter.viewholder.TheaterViewHolder

class TheaterListAdapter(onClick: (ListItem) -> Unit = {}) :
    BaseRecyclerView.Adapter<ListItem>(onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerView.BaseViewHolder =
        TheaterViewHolder(
            ItemTheaterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemViewClick
        )

    fun replaceList(newItems: List<ListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyItemRangeInserted(items.size - newItems.size, newItems.size)
    }
}
