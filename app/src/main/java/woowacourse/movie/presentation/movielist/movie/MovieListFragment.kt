package woowacourse.movie.presentation.movielist.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.movielist.cinema.CinemaBottomSheetFragment

class MovieListFragment : Fragment(), MovieListContract.View {

    override val presenter: MovieListContract.Presenter by lazy { MovieListPresenter(this) }

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovies()
    }

    override fun setMoviesAdapter(movieItems: List<MovieItem>) {
        binding.recyclerMainMovie.adapter = MovieItemAdapter(movieItems) { clickBook(it) }
    }

    private fun clickBook(movie: MovieModel) {
        val cinemaBottomSheet = CinemaBottomSheetFragment(movie)
        cinemaBottomSheet.show(
            requireActivity().supportFragmentManager,
            CinemaBottomSheetFragment.TAG,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
