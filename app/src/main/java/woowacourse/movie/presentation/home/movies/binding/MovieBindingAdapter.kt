package woowacourse.movie.presentation.home.movies.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.home.movies.adapter.MovieAdManager
import woowacourse.movie.presentation.home.movies.adapter.MoviesAdapter

@BindingAdapter("setItems")
fun setItems(
    rv: RecyclerView,
    movies: List<MovieUiModel>?,
) {
    if (movies == null) return

    val adapter = rv.adapter
    if (adapter is MoviesAdapter) {
        val adManager = MovieAdManager()
        val items = adManager.insertAds(movies)
        adapter.submitList(items)
    }
}
