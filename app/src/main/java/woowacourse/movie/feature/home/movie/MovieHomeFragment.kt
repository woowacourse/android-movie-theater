package woowacourse.movie.feature.home.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.movie.databinding.FragmentMovieHomeBinding
import woowacourse.movie.feature.home.movie.adapter.MovieAdapter
import woowacourse.movie.feature.home.theater.TheaterSelectionBottomSheetFragment
import woowacourse.movie.model.Movie
import woowacourse.movie.util.BaseFragment

class MovieHomeFragment : BaseFragment<MovieHomeContract.Presenter>(), MovieHomeContract.View {
    private var _binding: FragmentMovieHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieHomeBinding.inflate(inflater)

        initializeView()
        return binding.root
    }

    override fun initializePresenter() = MovieHomePresenter(this)

    private fun initializeView() {
        presenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        binding.movieRecyclerView.adapter =
            MovieAdapter(movies) { movieId ->
                displayTheaterSelectionDialog(movieId)
            }
    }

    override fun displayTheaterSelectionDialog(movieId: Long) {
        val fragment = TheaterSelectionBottomSheetFragment.newInstance(movieId)
        fragment.show(parentFragmentManager, fragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
