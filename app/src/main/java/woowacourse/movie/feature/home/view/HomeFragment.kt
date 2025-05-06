package woowacourse.movie.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.feature.bookingdetail.view.BookingDetailActivity
import woowacourse.movie.feature.home.contract.HomeContract
import woowacourse.movie.feature.home.presenter.HomePresenter
import woowacourse.movie.feature.home.view.adapter.Item
import woowacourse.movie.feature.home.view.adapter.MoviesAdapter
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.feature.theaters.view.TheatersDialogFragment

class HomeFragment :
    Fragment(),
    HomeContract.View {
    private lateinit var binding: FragmentHomeBinding
    private val moviesAdapter by lazy {
        MoviesAdapter { movie ->
            presenter.selectMovieForBooking(
                movie,
            )
        }
    }
    private val presenter: HomeContract.Presenter by lazy { HomePresenter(this).apply { prepareMovies() } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter
    }

    override fun showMovies(movies: List<MovieUiModel>) {
        moviesAdapter.submitList(Item.from(movies))
        binding.moviesAdapter = moviesAdapter
    }

    override fun showTheaters(screenings: List<ScreeningUiModel>) {
        val dialog =
            TheatersDialogFragment.newInstance(screenings).apply {
                navigateToBookingDetail = { selectedScreening ->
                    navigateToBookingDetail(selectedScreening)
                }
            }
        dialog.show(childFragmentManager, TheatersDialogFragment.TAG)
    }

    override fun navigateToBookingDetail(screening: ScreeningUiModel) {
        val intent = BookingDetailActivity.newIntent(requireContext(), screening)
        startActivity(intent)
    }
}
