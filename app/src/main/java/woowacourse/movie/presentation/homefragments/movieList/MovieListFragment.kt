package woowacourse.movie.presentation.homefragments.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.adapter.MovieAdapter
import woowacourse.movie.presentation.homefragments.movieList.fragment.TheaterBottomDialogFragment
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener
import woowacourse.movie.repository.DummyTheaterList

class MovieListFragment : Fragment(), MovieListContract.View, MovieListClickListener {
    private val presenter = MovieListPresenter(this)
    private lateinit var movieListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        movieListRecyclerView = view.findViewById(R.id.rv_movies)
        presenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        movieListRecyclerView.adapter = MovieAdapter(movies, this)
    }

    override fun ticketingButtonClick(movieId: Long) {
        val bottomSheet = TheaterBottomDialogFragment(DummyTheaterList, movieId)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }
}
