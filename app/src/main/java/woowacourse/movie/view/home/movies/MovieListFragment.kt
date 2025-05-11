package woowacourse.movie.view.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.view.home.model.FeedUiModel
import woowacourse.movie.view.home.movies.adapter.MovieAdapter
import woowacourse.movie.view.home.theaters.TheaterListFragment

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListContract.View, MovieListEventHandler {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: MovieListContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(this)
        presenter.loadMovies()
    }

    override fun showMovieList(movieList: List<FeedUiModel>) {
        binding.rvMovieList.adapter = MovieAdapter(movieList, this)
    }

    override fun moveToTheaterSelection(movieId: Int) {
        TheaterListFragment.newInstance(movieId).show(parentFragmentManager, THEATER_BOTTOM_SHEET)
    }

    override fun onMovieSelected(movieId: Int) {
        presenter.selectMovie(movieId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET = "BOTTOM_SHEET"
    }
}
