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
import woowacourse.movie.presentation.theater.TheaterFragment
import woowacourse.movie.presentation.adapter.MovieAdapter

class MoviesFragment :
    Fragment(),
    MoviesContract.View {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val presenter: MoviesPresenter by lazy { MoviesPresenter(this, MovieData) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initializeMovies()
    }

    override fun showMovies(moviesItems: List<MoviesItem>) {
        val adapter =
            MovieAdapter {
                presenter.selectMovie(it)
            }
        adapter.submitList(moviesItems)
        binding.recyclerviewMovies.adapter = adapter
    }

    override fun showTheaterSelectDialog(movie: Movie) {
        val theaterFragment = TheaterFragment.newInstance(movie)
        theaterFragment.show(childFragmentManager, theaterFragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
