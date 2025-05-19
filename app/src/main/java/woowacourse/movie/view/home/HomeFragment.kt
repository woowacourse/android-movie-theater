package woowacourse.movie.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieListItem
import woowacourse.movie.view.home.movies.OnMovieEventListener
import woowacourse.movie.view.home.movies.adapter.MovieAdapter
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment : Fragment(), HomeContract.View {
    private val presenter = HomePresenter(this)
    private lateinit var movieAdapter: MovieAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        presenter.loadMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showMovies(movieListItems: List<MovieListItem>) {
        movieAdapter =
            MovieAdapter(
                object : OnMovieEventListener {
                    override fun onClickShowTheater(movie: Movie) {
                        showTheaterSelectDialog(movie)
                    }
                },
            )
        binding.recyclerView.adapter = movieAdapter
        movieAdapter.submitList(movieListItems)
    }

    override fun showTheaterSelectDialog(movie: Movie) {
        val dialog =
            TheaterBottomSheetDialogFragment.newInstance(
                movie,
            )
        dialog.show(childFragmentManager, "TheaterBottomSheetDialog")
    }
}
