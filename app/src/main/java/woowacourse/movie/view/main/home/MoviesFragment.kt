package woowacourse.movie.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.model.database.DummyMovieDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.main.home.adapter.MovieAdapter
import woowacourse.movie.view.theater.TheaterBottomSheetDialogFragment
import woowacourse.movie.view.util.ExceptionMessages

class MoviesFragment :
    Fragment(),
    MovieContract.View {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() =
            _binding
                ?: throw IllegalStateException(ExceptionMessages.FRAGMENT_BINDING_STATE_EXCEPTION)
    private val presenter: MoviePresenter by lazy { MoviePresenter(this, DummyMovieDao) }
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
        _binding = FragmentMoviesBinding.inflate(layoutInflater)
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
        val bottomSheet = TheaterBottomSheetDialogFragment.Companion.newInstance(movie)
        bottomSheet.show(parentFragmentManager, BOTTOM_SHEET_TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMovieAdapter() {
        binding.rvMovies.adapter = moviesAdapter
    }

    companion object {
        private const val BOTTOM_SHEET_TAG = "theater_bottom_sheet"
    }
}
