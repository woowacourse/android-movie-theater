package woowacourse.movie.home.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.home.presenter.MovieHomePresenter
import woowacourse.movie.home.presenter.contract.MovieHomeContract
import woowacourse.movie.home.view.adapter.movie.MovieAdapter
import woowacourse.movie.home.view.listener.MovieHomeClickListener
import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class MovieHomeFragment : Fragment(), MovieHomeContract.View, MovieHomeClickListener {
    private lateinit var binding: FragmentMovieHomeBinding
    private lateinit var movieHomePresenter: MovieHomePresenter
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentMovieHomeBinding.inflate(inflater, container, false)
        initializeHomeFragment()
        return binding.root
    }

    private fun initializeHomeFragment() {
        movieAdapter = MovieAdapter(this)
        binding.movieRecyclerView.adapter = movieAdapter

        movieHomePresenter = MovieHomePresenter(this)
        movieHomePresenter.loadMovies()
    }

    override fun displayMovies(
        movies: List<Movie>,
        advertisements: List<Advertisement>,
    ) {
        movieAdapter.updateMovies(movies)
        movieAdapter.updateAdvertisements(advertisements)
    }

    override fun onReservationButtonClick(movieId: Long) {
        val bundle = Bundle()
        bundle.apply {
            putLong(KEY_MOVIE_ID, movieId)
        }
        val theaterSelectionFragment = TheaterSelectionFragment()
        theaterSelectionFragment.arguments = bundle
        theaterSelectionFragment.show(parentFragmentManager, theaterSelectionFragment.tag)
    }

    override fun onAdvertisementClick(advertisement: Advertisement) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisement.link))
        startActivity(intent)
    }
}
