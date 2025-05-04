package woowacourse.movie.presentation.view.home.movies.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.view.home.movies.adapter.MovieAdManager
import woowacourse.movie.presentation.view.home.movies.adapter.MoviesAdapter

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
