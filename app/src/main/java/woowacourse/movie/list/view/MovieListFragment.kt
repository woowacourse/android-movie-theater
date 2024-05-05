package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.list.adapter.MovieListAdapter
import woowacourse.movie.list.adapter.OnItemClickListener
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.presenter.MovieListPresenter

class MovieListFragment : Fragment(), MovieListContract.View, OnItemClickListener {
    override val presenter = MovieListPresenter(this)
    private lateinit var binding: FragmentHomeBinding
    private var movieListAdapter: MovieListAdapter = MovieListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentHomeBinding.inflate(inflater, container, false)
        presenter.setMoviesInfo()

        return binding.root
    }

    override fun showMoviesInfo(
        movies: List<Movie>,
        advertisements: List<Advertisement>,
    ) {
        movieListAdapter.initMovieListInfo(movies, advertisements)
        binding.movieRecyclerView.adapter = movieListAdapter
        movieListAdapter.notifyDataSetChanged()
        binding.movieRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onClick(movieId: Long) {
        val theaterBottomSheetFragment = newFragmentInstance(movieId)
        theaterBottomSheetFragment.show(parentFragmentManager, theaterBottomSheetFragment::class.java.simpleName)
    }

    companion object {
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_MOVIE_ID_KEY_TO_FRAGMENT = "movie_id_key_to_fragment"

        fun newFragmentInstance(movieId: Long): TheaterBottomSheetFragment {
            return TheaterBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT, movieId)
                }
            }
        }
    }
}
