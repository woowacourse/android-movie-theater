package woowacourse.movie.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.home.presenter.MovieHomePresenter
import woowacourse.movie.home.presenter.contract.MovieHomeContract
import woowacourse.movie.home.view.adapter.movie.MovieAdapter
import woowacourse.movie.home.view.listener.ReservationButtonClickListener
import woowacourse.movie.model.Movie
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class MovieHomeFragment : Fragment(), MovieHomeContract.View {
    private lateinit var binding: FragmentMovieHomeBinding
    private lateinit var movieHomePresenter: MovieHomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_home, container, false)

        movieHomePresenter = MovieHomePresenter(this)
        movieHomePresenter.loadMovies()
        return binding.root
    }

    override fun displayMovies(movies: List<Movie>) {
        val reservationButtonClickListener =
            object : ReservationButtonClickListener {
                override fun onClick(movieId: Long) {
                    displayTheaterSelectionDialog(movieId)
                }
            }
        binding.movieRecyclerView.adapter = MovieAdapter(movies, reservationButtonClickListener)
    }

    override fun displayTheaterSelectionDialog(id: Long) {
        val bundle = Bundle()
        bundle.putLong(KEY_MOVIE_ID, id)
        val theaterSelectionFragment = TheaterSelectionFragment()
        theaterSelectionFragment.arguments = bundle
        theaterSelectionFragment.show(parentFragmentManager, theaterSelectionFragment.tag)
    }
}
