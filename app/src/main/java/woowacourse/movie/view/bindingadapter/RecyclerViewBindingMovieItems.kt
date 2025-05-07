package woowacourse.movie.view.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.view.home.movies.MovieItem
import woowacourse.movie.view.home.movies.OnMovieEventListener
import woowacourse.movie.view.home.movies.adapter.MovieAdapter

@BindingAdapter(value = ["items", "itemClickListener"])
fun setRecyclerViewItem(
    recyclerView: RecyclerView,
    items: List<MovieItem>,
    itemClickListener: OnMovieEventListener,
) {
    val adapter = recyclerView.adapter
    if (adapter == null) {
        val movieAdapter = MovieAdapter(itemClickListener)
        recyclerView.adapter = movieAdapter
        movieAdapter.submitList(items)
    } else if (adapter is MovieAdapter) {
        adapter.submitList(items)
    }
}
