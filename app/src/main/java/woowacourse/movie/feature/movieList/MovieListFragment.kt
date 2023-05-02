package woowacourse.movie.feature.movieList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.adv.AdvDetailActivity
import woowacourse.movie.feature.detail.MovieDetailActivity
import woowacourse.movie.feature.main.MainActivity
import woowacourse.movie.feature.movieList.adapter.MovieListAdapter
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

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
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MainActivity.KEY_MOVIE, movie)
        startActivity(intent)
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        val intent = Intent(activity, AdvDetailActivity::class.java)
        intent.putExtra(MainActivity.KEY_ADV, adbState)
        startActivity(intent)
    }

    companion object {

        internal const val KEY_MOVIE = "key_movie"
        internal const val KEY_ADV = "key_adb"
    }
}
