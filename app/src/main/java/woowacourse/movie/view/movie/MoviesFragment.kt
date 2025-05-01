package woowacourse.movie.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
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
        MovieAdapter(
            object : MovieClickListener {
                override fun onReservationClick(movie: Movie) {
                    showBottomSheetDialog(movie)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movies,
                container,
                false,
            )

        binding.fragmentMovies = this

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
