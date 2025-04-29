package woowacourse.movie.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.view.movie.adapter.MovieAdapter
import woowacourse.movie.view.reservation.reservation.ReservationFragment

class MoviesFragment :
    Fragment(),
    MovieContract.View {
    private val presenter: MoviePresenter by lazy { MoviePresenter(this) }
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        (activity as? MoviesActivity)?.showBottomNav(true)
    }

    override fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    override fun navigateToReservation(movie: Movie) {
        val bundle = bundleOf("movieKey" to movie)
        parentFragmentManager.setFragmentResult("requestKey", bundle)

        (requireActivity() as? MoviesActivity)?.replaceFragment(ReservationFragment())
    }

    private fun setupMovieAdapter() {
        val recyclerView = binding.rvMovies
        moviesAdapter =
            MovieAdapter(
                object : MovieClickListener {
                    override fun onReservationClick(movie: Movie) {
                        navigateToReservation(movie)
                    }
                },
            )
        recyclerView.adapter = moviesAdapter
    }
}
