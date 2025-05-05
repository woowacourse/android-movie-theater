package woowacourse.movie.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.presenter.home.HomeContracts
import woowacourse.movie.presenter.home.HomePresenter
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment :
    Fragment(R.layout.fragment_home),
    HomeContracts.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val presenter: HomeContracts.Presenter = HomePresenter(this)
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        presenter.updateView()
    }

    override fun showMovies(movies: List<MovieType>) {
        if (::movieAdapter.isInitialized.not()) {
            movieAdapter =
                MovieAdapter(
                    movieClickListener =
                        object : MovieClickListener {
                            override fun onReservationClick(movieId: Long) {
                                presenter.onTheaterRequested(movieId)
                            }

                            override fun onAdvertisementClick(url: String) {
                                presenter.onAdvertisementRequested(url)
                            }
                        },
                )
        }
        binding.rvMainMovies.adapter = movieAdapter
        movieAdapter.submitList(movies)
    }

    override fun showTheaters(theaterMovieSchedules: TheaterMovieSchedules) {
        TheaterBottomSheetDialogFragment
            .newInstance(theaterMovieSchedules)
            .show(parentFragmentManager, "jay")
    }

    override fun showAdvertisement(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
