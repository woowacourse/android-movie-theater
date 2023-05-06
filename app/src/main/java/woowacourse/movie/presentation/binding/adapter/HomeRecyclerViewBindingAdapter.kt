package woowacourse.movie.presentation.binding.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.views.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.views.main.fragments.home.recyclerview.MovieListAdapter
import woowacourse.movie.presentation.views.main.fragments.home.recyclerview.OnEndScrollListener

@BindingAdapter("app:presenter")
fun RecyclerView.setPresenter(presenter: HomeContract.Presenter) {
    if (adapter == null) {
        val movieAdapter = MovieListAdapter(
            adTypes = presenter.loadAds(),
            onItemClick = presenter::onItemClick
        )
        adapter = movieAdapter
        appendAll(movieAdapter, presenter)
        addOnScrollListener(
            OnEndScrollListener { appendAll(movieAdapter, presenter) }
        )
    }
}

private fun appendAll(adapter: MovieListAdapter, presenter: HomeContract.Presenter) {
    adapter.appendAll(presenter.loadMoreMovies())
}
