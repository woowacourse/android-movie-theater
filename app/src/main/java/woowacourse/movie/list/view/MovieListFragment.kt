package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.list.adapter.MovieListAdapter
import woowacourse.movie.list.adapter.MovieOnItemClickListener
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.TheaterContent
import woowacourse.movie.list.presenter.MovieListPresenter
import woowacourse.movie.list.view.TheaterBottomSheetFragment.Companion.newFragmentInstance

class MovieListFragment : Fragment(), MovieListContract.View, MovieOnItemClickListener {
    override val presenter = MovieListPresenter(this)
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        binding.movieListfragment = this
        presenter.setMovieListAdapter()
        presenter.setMoviesInfo()

        return binding.root
    }

    override fun linkMovieListAdapter(theaterContent: List<TheaterContent>) {
        movieListAdapter =
            MovieListAdapter(theaterContent, this)
    }

    override fun showMoviesList() {
        binding.movieRecyclerView.adapter = movieListAdapter
    }

    override fun updateMovieItems(theaterContent: List<TheaterContent>) {
        movieListAdapter.updateItems(theaterContent)
    }

    override fun onClick(movieId: Long) {
        val theaterBottomSheetFragment = newFragmentInstance(movieId)
        theaterBottomSheetFragment.show(
            childFragmentManager,
            theaterBottomSheetFragment::class.java.simpleName
        )
    }
}
