package woowacourse.movie.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.view.home.movies.MovieItem
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.OnMovieEventListener
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment : Fragment(), HomeContract.View {
    private val presenter = HomePresenter(this)
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        presenter.fetchData()
    }

    override fun showMoviesScreen(movieItems: List<MovieItem>) {
        binding.movieItems = movieItems
        binding.onItemClick = OnMovieEventListener { movieUi -> showTheaterSelectDialog(movieUi) }
    }

    private fun showTheaterSelectDialog(movieUi: MovieUi) {
        val dialog =
            TheaterBottomSheetDialogFragment.newInstance(movieUi.movieId)
        dialog.show(childFragmentManager, "TheaterBottomSheetDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
