package woowacourse.movie.feature.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.adv.AdvDetailActivity
import woowacourse.movie.feature.common.adapter.CommonListAdapter
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.reservation.MovieDetailActivity
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListContract.View {

    private lateinit var movieListView: RecyclerView
    private lateinit var adapter: CommonListAdapter
    private lateinit var presenter: MovieListContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override fun onResume() {
        super.onResume()
        presenter.setListItems()
    }

    private fun init(view: View) {
        presenter = MovieListPresenter(this)
        movieListView = view.findViewById(R.id.rv_main)
        adapter = CommonListAdapter()
        movieListView.adapter = adapter
    }

    override fun setItems(items: List<ItemModel>) { adapter.setItems(items) }

    override fun navigateMovieDetail(movie: MovieState) {
        MovieDetailActivity.startActivity(requireContext(), movie)
    }

    override fun navigateAdvDetail(adv: AdvState) {
        AdvDetailActivity.startActivity(requireContext(), adv)
    }
}
