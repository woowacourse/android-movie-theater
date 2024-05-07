package woowacourse.movie.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.list.adapter.MovieListAdapter
import woowacourse.movie.list.adapter.OnItemClickListener
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.TheaterContent
import woowacourse.movie.list.presenter.MovieListPresenter
import woowacourse.movie.list.view.TheaterBottomSheetFragment.Companion.newFragmentInstance

class MovieListFragment : Fragment(), MovieListContract.View, OnItemClickListener {
    override val presenter = MovieListPresenter(this)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.movieListfragment = this
        presenter.setMovieListAdapter()
        presenter.setMoviesInfo()

        return binding.root
    }

    override fun makeMovieListAdapter(
        theaterContent: List<TheaterContent>,
    ) {
        movieListAdapter =
            MovieListAdapter(theaterContent = theaterContent, movieHomeClickListener = this)
    }

    override fun showMoviesInfo() {
        binding.movieRecyclerView.adapter = movieListAdapter
    }

    override fun updateMovieEntity(theaterContent: List<TheaterContent>) {
        movieListAdapter.updateItems(theaterContent)
    }

    override fun onClick(movieId: Long) {
        val theaterBottomSheetFragment = newFragmentInstance(movieId)
        theaterBottomSheetFragment.show(
            parentFragmentManager,
            theaterBottomSheetFragment::class.java.simpleName
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_MOVIE_ID_KEY_TO_FRAGMENT = "movie_id_key_to_fragment"
    }
}
