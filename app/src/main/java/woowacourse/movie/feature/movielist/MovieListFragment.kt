package woowacourse.movie.feature.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.adv.AdvDetailActivity
import woowacourse.movie.feature.common.adapter.CommonListAdapter
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.reservation.MovieDetailActivity
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var movieListView: RecyclerView
    private lateinit var adapter: CommonListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        movieListView = view.findViewById(R.id.rv_main)
        initMovieListView()
    }

    private fun initMovieListView() {
        val itemModels: MutableList<ItemModel> =
            MovieRepository.allMovies().map { movieState ->
                movieState.toItemModel { navigateMovieDetail(movieState) }
            }.toMutableList()
        val advItemModels: List<ItemModel> =
            AdvRepository.allAdv().map { advState ->
                advState.toItemModel { navigateAdbDetail(advState) }
            }

        var itemPosition = 0
        var advPosition = 0
        while (itemPosition < itemModels.size) {
            if ((itemPosition + 1) % 4 == 0) {
                itemModels.add(itemPosition, advItemModels[advPosition])
                advPosition++
            }
            if (advPosition == advItemModels.size) advPosition = 0
            itemPosition++
        }

        adapter = CommonListAdapter(itemModels)
        movieListView.adapter = adapter
    }

    private fun navigateMovieDetail(movie: MovieState) {
        MovieDetailActivity.startActivity(requireContext(), movie)
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        AdvDetailActivity.startActivity(requireContext(), adbState)
    }
}
