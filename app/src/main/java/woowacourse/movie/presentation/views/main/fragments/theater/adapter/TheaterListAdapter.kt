package woowacourse.movie.presentation.views.main.fragments.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.views.main.fragments.theater.adapter.viewholder.TheaterViewHolder

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

    fun appendAll(items: List<ListItem>) {
        this.items.addAll(items)
        notifyItemRangeInserted(this.items.size - items.size, items.size)
    }
}
