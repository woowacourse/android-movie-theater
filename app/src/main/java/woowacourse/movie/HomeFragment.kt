package woowacourse.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.booking.BookingActivity
import woowacourse.movie.view.movies.MovieListContract
import woowacourse.movie.view.movies.MovieListPresenter
import woowacourse.movie.view.movies.adapter.MovieAdapter
import woowacourse.movie.view.movies.bottomsheet.TheaterBottomSheet
import woowacourse.movie.view.movies.model.ScreeningInfo
import woowacourse.movie.view.movies.model.UiModel

class HomeFragment : Fragment(R.layout.fragment_home), MovieListContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val presenter: MovieListContract.Presenter by lazy {
        MovieListPresenter(this, MovieStore(), TheaterStore())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadUiData()
    }

    override fun showMovieList(movieList: List<UiModel>) {
        val rv = binding.rv
        val adapter =
            MovieAdapter(
                itemsList = movieList,
                onClickBooking = {
                    presenter.loadTheaters(it)
                },
            )
        rv.adapter = adapter
    }

    override fun showTheaterBottomSheet(
        movieId: Int,
        theaters: Theaters,
    ) {
        TheaterBottomSheet(
            theaters,
            movieId,
            onclick = {
                presenter.loadMovieScreening(movieId, it)
            },
        ).show(childFragmentManager, THEATER_BOTTOM_SHEET)
    }

    override fun moveToBooking(screening: ScreeningInfo) {
        val intent = BookingActivity.newIntent(requireContext(), screening)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET = "BOTTOM_SHEET"
    }
}
