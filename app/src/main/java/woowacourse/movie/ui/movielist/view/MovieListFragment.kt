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
import woowacourse.movie.ui.movielist.contract.MovieListContract
import woowacourse.movie.ui.movielist.presenter.MovieListPresenter
import woowacourse.movie.ui.movielist.view.TheaterBottomSheetDialogFragment.Companion.THEATER_DIALOG_TAG

class MovieListFragment :
    Fragment(),
    MovieListContract.View {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val movieListPresenter by lazy { MovieListPresenter(this) }
    private val movieAdapter by lazy { generateMovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.adapter = movieAdapter
        movieListPresenter.loadMovieList()
    }

    override fun showMoveListItems(items: List<MovieListItem>) {
        movieAdapter.submitList(items)
    }

    override fun showTheaters(movieId: Long) {
        val theaterFragment = TheaterBottomSheetDialogFragment.newInstance(movieId)
        theaterFragment.show(childFragmentManager, THEATER_DIALOG_TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateMovieAdapter(): MovieAdapter {
        return MovieAdapter(
            onClickBooking = { movieId ->
                movieListPresenter.startBooking(movieId)
            },
        )
    }
}
