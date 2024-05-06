package woowacourse.movie.presentation.homefragments.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.adapter.MovieAdapter
import woowacourse.movie.presentation.homefragments.movieList.fragment.TheaterBottomDialogFragment
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener

class MovieListFragment : Fragment(), MovieListContract.View, MovieListClickListener {
    private var _binding: FragmentMovieListBinding? = null
    val binding get() = _binding!!

    private val presenter = MovieListPresenter(this)
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.adapter = movieAdapter
        presenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        movieAdapter.updateMovies(movies)
    }

    override fun ticketingButtonClick(movieId: Long) {
        val bottomSheet = TheaterBottomDialogFragment.newInstance(movieId)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
