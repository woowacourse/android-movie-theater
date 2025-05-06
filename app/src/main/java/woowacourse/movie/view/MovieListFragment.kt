package woowacourse.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.movies.MovieListAdapter
import woowacourse.movie.view.movies.MovieListItem
import woowacourse.movie.view.movies.MoviesContract
import woowacourse.movie.view.movies.MoviesPresenter
import woowacourse.movie.view.movies.OnMovieEventListener
import woowacourse.movie.view.movies.cinema.CinemaSelectionFragment

class MovieListFragment :
    Fragment(),
    MoviesContract.View {
    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding!!

    private lateinit var presenter: MoviesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MoviesPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showMovies(movies: List<MovieListItem>) {
        val adapter =
            MovieListAdapter(
                movies,
                object : OnMovieEventListener {
                    override fun onReserveButtonClick(movie: Movie) {
                        val instance =
                            CinemaSelectionFragment.newInstance(
                                movie.screening,
                            )
                        instance.show(childFragmentManager, "CinemaSelectionFragment")
                    }
                },
            )
        binding.rvMainMovies.adapter = adapter
    }
}
