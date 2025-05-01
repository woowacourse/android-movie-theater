package woowacourse.movie.ui.movielist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.domain.model.MovieListItem
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.booking.view.BookingActivity
import woowacourse.movie.ui.movielist.contract.MovieListContract
import woowacourse.movie.ui.movielist.presenter.MovieListPresenter

class MovieListFragment :
    Fragment(),
    MovieListContract.View {
    private lateinit var binding: FragmentMovieListBinding
    private val movieListPresenter = MovieListPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        movieListPresenter.loadMovieList()
        return binding.root
    }

    override fun startBookingActivity(theater: Theater) {
        startActivity(BookingActivity.newIntent(requireActivity(), theater))
    }

    override fun setMoveListItems(items: List<MovieListItem>) {
        val adapter =
            MovieAdapter(
                onClickBooking = { movie ->
                    val theaterFragment = TheaterBottomSheetDialogFragment.newInstance(movie)
                    theaterFragment.show(childFragmentManager, "dialog")
                },
            )

        binding.moviesRecyclerView.adapter = adapter
        adapter.submitList(items)
    }
}
