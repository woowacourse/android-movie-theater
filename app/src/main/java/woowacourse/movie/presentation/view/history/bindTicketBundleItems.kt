package woowacourse.movie.presentation.view.history

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.TicketBundleUiModel

@BindingAdapter("setItems")
fun bindTicketBundleItems(
    recyclerView: RecyclerView,
    items: List<TicketBundleUiModel>?,
) {
    val adapter = recyclerView.adapter as? TicketBundleAdapter
    adapter?.submitList(items ?: emptyList())
}
