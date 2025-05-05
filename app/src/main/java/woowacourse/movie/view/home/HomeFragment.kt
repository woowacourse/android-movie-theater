package woowacourse.movie.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.presenter.home.HomeContracts
import woowacourse.movie.presenter.home.HomePresenter
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment :
    Fragment(),
    HomeContracts.View {
    private lateinit var binding: FragmentHomeBinding
    private val presenter: HomeContracts.Presenter = HomePresenter(this)
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter =
            MovieAdapter(
                movieClickListener =
                    object : MovieClickListener {
                        override fun onReservationClick(movieId: Long) {
                            presenter.onTheaterRequested(movieId)
                        }
                    },
                advertisementClickListener = {
                    presenter.requestAdvertisement(
                        ADVERTISEMENT_URL,
                    )
                },
            )

        presenter.initView()
    }

    override fun showMovies(movies: List<Movie>) {
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

    companion object {
        private const val ADVERTISEMENT_URL = "https://www.woowacourse.io/"
    }
}
