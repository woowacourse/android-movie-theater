package woowacourse.movie.presentation.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.MovieItemData
import woowacourse.movie.presentation.booking.BookingActivity
import woowacourse.movie.presentation.model.MovieModel

class MovieListFragment : Fragment(), MovieListContract.View {
    override val presenter: MovieListContract.Presenter by lazy { MovieListPresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.requestMovies()
    }

    override fun setMoviesAdapter(movies: List<MovieModel>) {
        requireActivity().findViewById<RecyclerView>(R.id.recyclerMainMovie).adapter =
            MovieItemAdapter(MOVIE_ITEMS) { clickBook(it) }
    }

    private fun clickBook(movieId: Long) {
        startActivity(BookingActivity.getIntent(requireActivity(), movieId))
    }

    companion object {
        private val MOVIE_ITEMS = MovieItemData.getMovieItems()
    }
}
