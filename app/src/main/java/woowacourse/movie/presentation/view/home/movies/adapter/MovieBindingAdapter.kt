package woowacourse.movie.presentation.view.home.movies.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.MovieUiModel

@BindingAdapter("setItems")
fun setItems(
    rv: RecyclerView,
    movies: List<MovieUiModel>?,
) {
    if (movies == null) return

    val adapter = rv.adapter
    if (adapter is MoviesAdapter) {
        adapter.submitList(movies.toList())
    }
}
