package woowacourse.movie.presentation.binding.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.ListItem

@BindingAdapter(
    "app:adapter",
    "app:hasDivider",
    "app:onAdapted",
    "app:onListItemClick",
    requireAll = false
)
fun RecyclerView.setRecyclerViewAdapter(
    recyclerViewAdapter: BaseRecyclerView.Adapter<ListItem>,
    hasDivider: Boolean = false,
    onAdapted: () -> Unit = {},
    onListItemClick: OnListItemClickListener,
) {
    if (hasDivider) {
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    if (adapter == null) {
        recyclerViewAdapter.onItemClick = onListItemClick::onListItemClick
        adapter = recyclerViewAdapter
        onAdapted()
    }
}

interface OnListItemClickListener {
    fun onListItemClick(item: ListItem)
}
