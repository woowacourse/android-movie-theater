package woowacourse.movie.ui.fragment.movieList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adv.AdvDetailActivity
import woowacourse.movie.ui.main.adapter.MovieListAdapter
import woowacourse.movie.ui.main.itemModel.AdvItemModel
import woowacourse.movie.ui.main.itemModel.MovieItemModel
import woowacourse.movie.ui.reservation.MovieDetailActivity

class MovieListFragment : Fragment() {

    private lateinit var movieListView: RecyclerView
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListView = view.findViewById(R.id.rv_main)
        adapter = MovieListAdapter(
            movie = MovieRepository.allMovies().map {
                it.convertToItemModel { position ->
                    navigateMovieDetail((adapter.items[position] as MovieItemModel).movieState)
                }
            },
            adv = AdvRepository.allAdv().map {
                it.convertToItemModel { position ->
                    navigateAdbDetail((adapter.items[position] as AdvItemModel).advState)
                }
            }
        )
        movieListView.adapter = adapter
    }

    private fun navigateMovieDetail(movie: MovieState) {
        startActivity(MovieDetailActivity.getIntent(activity as Context, movie))
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        startActivity(AdvDetailActivity.getIntent(activity as Context, adbState))
    }
}
