package woowacourse.movie.view.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.Showing
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter
import woowacourse.movie.view.home.theater.OnTheaterEventListener

@BindingAdapter(value = ["items", "itemClickListener"])
fun setRecyclerViewItem(
    recyclerView: RecyclerView,
    items: List<Showing>,
    itemClickListener: OnTheaterEventListener,
) {
    val adapter = recyclerView.adapter
    if (adapter == null) {
        val theaterAdapter = TheaterAdapter(itemClickListener)
        recyclerView.adapter = theaterAdapter
        theaterAdapter.submitList(items)
    } else if (adapter is TheaterAdapter) {
        adapter.submitList(items)
    }
}
