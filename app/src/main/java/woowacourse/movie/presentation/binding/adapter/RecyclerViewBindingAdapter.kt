package woowacourse.movie.presentation.binding.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.ui.main.fragments.home.recyclerview.OnEndScrollListener

@BindingAdapter(
    "app:adapter",
    "app:hasDivider",
    "app:onAdapted",
    "app:onEndScroll",
    "app:onListItemClick",
    requireAll = false
)
fun RecyclerView.setRecyclerViewAdapter(
    recyclerViewAdapter: BaseRecyclerView.Adapter<ListItem>,
    hasDivider: Boolean = false,
    onAdaptedListener: OnAdaptedListener?,
    onEndScroll: (() -> Unit)?,
    onListItemClick: OnListItemClickListener?,
) {
    if (hasDivider) {
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    if (adapter == null) {
        onListItemClick?.run { recyclerViewAdapter.onItemClick = ::onListItemClick }
        adapter = recyclerViewAdapter
        onAdaptedListener?.onAdapted()
    }

    onEndScroll?.run { addOnScrollListener(OnEndScrollListener(this)) }
}

interface OnListItemClickListener {
    fun onListItemClick(item: ListItem)
}

interface OnAdaptedListener {
    fun onAdapted()
}
