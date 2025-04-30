package woowacourse.movie.feature.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Screenings
import woowacourse.movie.feature.TheatersDialogFragment
import woowacourse.movie.feature.bookingdetail.view.BookingDetailActivity
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.movies.contract.MoviesContract
import woowacourse.movie.feature.movies.presenter.MoviesPresenter
import woowacourse.movie.feature.movies.view.adapter.Item
import woowacourse.movie.feature.movies.view.adapter.MoviesAdapter

class HomeFragment :
    Fragment(),
    MoviesContract.View {
    private val presenter: MoviesContract.Presenter by lazy { MoviesPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.prepareMovies()
    }

    override fun showMovies(movies: List<MovieUiModel>) {
        val moviesAdapter = MoviesAdapter { movie -> presenter.selectMovieForBooking(movie) }
        moviesAdapter.submitList(Item.from(movies))
        view?.findViewById<RecyclerView>(R.id.rv_home_movies)?.adapter = moviesAdapter
    }

    override fun showTheaters(screenings: Screenings) {
        TheatersDialogFragment(screenings) { screening ->
            navigateToBookingDetail(screening)
        }.show(childFragmentManager, TheatersDialogFragment.TAG)
    }

    override fun navigateToBookingDetail(screening: Screening) {
        val intent = BookingDetailActivity.newIntent(requireContext(), screening)
        startActivity(intent)
    }
}
