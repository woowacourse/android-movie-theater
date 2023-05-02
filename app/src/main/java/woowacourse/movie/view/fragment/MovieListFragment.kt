package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListViewType
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.view.activity.MovieReservationActivity
import woowacourse.movie.view.adapter.MovieAdapter

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMovieRecyclerView(view)
    }

    private fun makeMovieRecyclerView(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.movie_list_recycler)
        movieRecyclerView.adapter =
            MovieAdapter(::onClickItem).also { it.presenter.setMovieList() }
    }

    private fun onClickItem(data: MovieListItemViewData) {
        when (data.viewType) {
            MovieListViewType.MOVIE -> MovieReservationActivity.from(
                requireContext(), data as MovieViewData
            ).run {
                startActivity(this)
            }
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }
}
