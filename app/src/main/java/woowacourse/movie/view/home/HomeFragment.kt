package woowacourse.movie.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.view.home.movies.MovieItem
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.adapter.MovieAdapter
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment : Fragment(), HomeContract.View {
    private val presenter = HomePresenter(this)
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        presenter.fetchData()
    }

    override fun showMoviesScreen(movieUis: List<MovieUi>) {
        val recyclerView: RecyclerView = binding.root.findViewById(R.id.recycler_view)
        val movieAdapter: MovieAdapter =
            MovieAdapter { movieUi -> showTheaterSelectDialog(movieUi) }

        val movieItems = mutableListOf<MovieItem>()
        movieUis.forEachIndexed { index, movieUi ->
            movieItems.add(MovieItem.ScreeningMovieUi(movieUi))
            if ((index + 1) % 3 == 0) {
                movieItems.add(MovieItem.Advertisement)
            }
        }
        recyclerView.adapter = movieAdapter
        movieAdapter.submitList(movieItems)
    }

    private fun showTheaterSelectDialog(movieUi: MovieUi) {
        val dialog =
            TheaterBottomSheetDialogFragment.newInstance(movieUi.movieId)
        dialog.show(childFragmentManager, "TheaterBottomSheetDialog")
    }
}
