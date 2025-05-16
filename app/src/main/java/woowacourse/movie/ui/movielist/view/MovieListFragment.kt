package woowacourse.movie.ui.movielist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.data.repository.MovieRepositoryImpl
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.domain.model.item.MovieListItem
import woowacourse.movie.ui.movielist.contract.MovieListContract
import woowacourse.movie.ui.movielist.presenter.MovieListPresenter

class MovieListFragment :
    Fragment(),
    MovieListContract.View {
    private val database by lazy { (requireActivity().application as MovieApplication).database }
    private lateinit var binding: FragmentMovieListBinding
    private val movieListPresenter by lazy {
        MovieListPresenter(this, MovieRepositoryImpl(database.movieDao()))
    }
    private val adapter: MovieAdapter by lazy {
        MovieAdapter(
            onClickBooking =
                BookingButtonClickListener { movie ->
                    val theaterFragment = TheaterBottomSheetDialogFragment.newInstance(movie)
                    theaterFragment.show(childFragmentManager, "dialog")
                },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        movieListPresenter.loadMovieList()
        return binding.root
    }

    override fun setMoveListItems(items: List<MovieListItem>) {
        binding.moviesRecyclerView.adapter = adapter
        adapter.submitList(items)
    }
}
