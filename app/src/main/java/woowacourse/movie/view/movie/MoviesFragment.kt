package woowacourse.movie.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.view.model.MovieListItem
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.movie.adapter.MovieAdapter
import woowacourse.movie.view.theater.TheaterBottomSheetDialogFragment

class MoviesFragment :
    Fragment(),
    MovieContract.View,
    MovieAdapter.Handler {
    private lateinit var binding: FragmentMoviesBinding
    private val presenter: MovieContract.Presenter by lazy { MoviePresenter(this) }
    private val moviesAdapter: MovieAdapter by lazy { MovieAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
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

    override fun showMovies(items: List<MovieListItem>) {
        moviesAdapter.submitList(items)
    }

    override fun showTheaterInfo(movie: MovieUiModel) {
        val bottomSheet = TheaterBottomSheetDialogFragment.newInstance(movie)
        bottomSheet.show(parentFragmentManager, BOTTOM_SHEET_TAG)
    }

    override fun onMovieClicked(movie: MovieUiModel) {
        presenter.reservationSelected(movie)
    }

    private fun setupMovieAdapter() {
        binding.rvMovies.adapter = moviesAdapter
    }

    companion object {
        private const val BOTTOM_SHEET_TAG = "theater_bottom_sheet"
    }
}
