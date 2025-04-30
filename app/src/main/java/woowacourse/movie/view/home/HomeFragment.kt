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
import woowacourse.movie.model.theater.MovieScreeningInfoByTheaters
import woowacourse.movie.presenter.movies.MoviesContracts
import woowacourse.movie.presenter.movies.MoviesPresenter
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment : Fragment(), MoviesContracts.View {
    private lateinit var binding: FragmentHomeBinding
    private val presenter: MoviesContracts.Presenter = MoviesPresenter(this)
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

        presenter.initView()
    }

    override fun showMovies(movies: List<Movie>) {
        if (::movieAdapter.isInitialized.not()) {
            movieAdapter =
                MovieAdapter(
                    movies = mutableListOf(),
                    movieClickListener =
                        object : MovieClickListener {
                            override fun onReservationClick(movieId: Long) {
                                presenter.onTheaterRequested(movieId)
                            }
                        },
                    advertisementClickListener = {
                        presenter.onAdvertisementRequested(
                            ADVERTISEMENT_URL,
                        )
                    },
                )
            binding.rvMainMovies.adapter = movieAdapter
        }
        movieAdapter.updateMovies(movies)
    }

    override fun showTheaters(movieScreeningInfoByTheaters: MovieScreeningInfoByTheaters) {
        val bundle = Bundle()
        TheaterBottomSheetDialogFragment().apply {
            bundle.putSerializable("theaters", movieScreeningInfoByTheaters)
            arguments = bundle
        }.show(
            parentFragmentManager,
            "jay",
        )
    }

    override fun showAdvertisement(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    companion object {
        private const val ADVERTISEMENT_URL = "https://www.woowacourse.io/"
    }
}
