package woowacourse.movie.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.MovieData
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.movies.adapter.MovieAdapter
import woowacourse.movie.presentation.movies.adapter.MovieListItem
import woowacourse.movie.presentation.theater.TheaterFragment

class MoviesFragment :
    Fragment(),
    MoviesContract.View {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var presenter: MoviesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MoviesPresenter(this, MovieData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun showMovies(movieListItems: List<MovieListItem>) {
        val adapter =
            MovieAdapter {
                presenter.onMovieClicked(it)
            }
        adapter.submitList(movieListItems)
        binding.recyclerviewMovies.adapter = adapter
    }

    override fun showTheaterSelectDialog(movie: Movie) {
        val theaterFragment = TheaterFragment.newInstance(movie)
        theaterFragment.show(childFragmentManager, theaterFragment.tag)
    }
}
