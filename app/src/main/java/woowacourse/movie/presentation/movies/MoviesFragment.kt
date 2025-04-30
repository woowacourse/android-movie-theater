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
import woowacourse.movie.presentation.theater.TheaterSelectFragment
import woowacourse.movie.ui.adapter.MovieAdapter

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

    override fun showMovies(moviesItems: List<MoviesItem>) {
        val adapter =
            MovieAdapter {
                presenter.onMovieClicked(it)
            }
        adapter.submitList(moviesItems)
        binding.recyclerviewMovies.adapter = adapter
    }

    override fun showTheaterSelectDialog(movie: Movie) {
        val theaterSelectFragment = TheaterSelectFragment.newInstance(movie)
        theaterSelectFragment.show(childFragmentManager, theaterSelectFragment.tag)
    }
}
