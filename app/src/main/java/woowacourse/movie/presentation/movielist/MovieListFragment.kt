package woowacourse.movie.presentation.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.presentation.booking.BookingActivity

class MovieListFragment : Fragment(), MovieListContract.View {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override val presenter: MovieListContract.Presenter by lazy { MovieListPresenter(this) }

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

        presenter.requestMovies()
    }

    override fun setMoviesAdapter(movieItems: List<MovieItem>) {
        binding.recyclerMainMovie.adapter = MovieItemAdapter(movieItems) {
            clickBook(it)
        }
    }

    private fun clickBook(movieId: Long) {
        startActivity(BookingActivity.getIntent(requireActivity(), movieId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
