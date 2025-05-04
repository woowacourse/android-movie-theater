package woowacourse.movie.view.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.MovieStore
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.view.home.model.UiModel
import woowacourse.movie.view.home.movies.adapter.MovieAdapter
import woowacourse.movie.view.home.theaters.TheaterBottomSheet

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListContract.View, MovieListEventHandler {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val presenter: MovieListContract.Presenter by lazy {
        MovieListPresenter(this, MovieStore())
    }

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
        presenter.loadMovies()
    }

    override fun showMovieList(movieList: List<UiModel>) {
        binding.rv.adapter = MovieAdapter(movieList, this)
    }

    override fun moveToTheaterSelection(movieId: Int) {
        TheaterBottomSheet.newInstance(movieId).show(childFragmentManager, THEATER_BOTTOM_SHEET)
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
