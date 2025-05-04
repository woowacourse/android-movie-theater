package woowacourse.movie.view.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.view.handler.MovieAdapterEventHandler
import woowacourse.movie.view.handler.TheaterAdapterEventHandler
import woowacourse.movie.view.home.booking.BookingActivity
import woowacourse.movie.view.home.movies.adapter.MovieAdapter
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter
import woowacourse.movie.view.home.movies.bottomsheet.TheaterBottomSheet
import woowacourse.movie.view.home.movies.model.MovieRvItem
import woowacourse.movie.view.home.movies.model.ScreeningInfo
import woowacourse.movie.view.home.movies.model.TheaterRvItem

class MovieListFragment : Fragment(R.layout.fragment_home), MovieListContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var movieAdapterEventHandler: MovieAdapter.Handler
    private lateinit var theaterAdapterEventHandler: TheaterAdapter.Handler

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
        presenter = MovieListPresenter.initialize(this)
        movieAdapterEventHandler = MovieAdapterEventHandler(presenter)
        theaterAdapterEventHandler = TheaterAdapterEventHandler(presenter)
        presenter.loadUiData()
    }

    override fun showMovieList(movieList: List<MovieRvItem>) {
        val rv = binding.rv
        val adapter =
            MovieAdapter(
                movieRvItems = movieList,
                handler = movieAdapterEventHandler,
            )
        rv.adapter = adapter
    }

    override fun showTheaterBottomSheet(
        movieId: Int,
        theaters: List<TheaterRvItem.TheaterItem>,
    ) {
        TheaterBottomSheet(
            theaters,
            theaterAdapterEventHandler,
        ).show(
            childFragmentManager,
            THEATER_BOTTOM_SHEET,
        )
    }

    override fun moveToBooking(screening: ScreeningInfo) {
        val intent = BookingActivity.Companion.newIntent(requireContext(), screening)
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
