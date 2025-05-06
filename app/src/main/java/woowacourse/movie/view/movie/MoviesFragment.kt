package woowacourse.movie.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.view.movie.adapter.MovieAdapter
import woowacourse.movie.view.theater.TheaterBottomSheetDialogFragment

class MoviesFragment :
    Fragment(),
    MovieContract.View {
    private lateinit var binding: FragmentMoviesBinding
    private val presenter: MoviePresenter by lazy { MoviePresenter(this) }
    private val moviesAdapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            showBottomSheetDialog(movie)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieAdapter()
        presenter.fetchMovies()
    }

    override fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    override fun showBottomSheetDialog(movie: Movie) {
        val bottomSheet = TheaterBottomSheetDialogFragment.newInstance(movie)
        bottomSheet.show(parentFragmentManager, BOTTOM_SHEET_TAG)
    }

    private fun setupMovieAdapter() {
        binding.rvMovies.adapter = moviesAdapter
    }

    companion object {
        private const val BOTTOM_SHEET_TAG = "theater_bottom_sheet"
    }
}
