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
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieItem
import woowacourse.movie.domain.Showings
import woowacourse.movie.view.movies.HomeContract
import woowacourse.movie.view.movies.HomePresenter
import woowacourse.movie.view.movies.OnBottomSheetDialogEventListener
import woowacourse.movie.view.movies.OnMovieEventListener
import woowacourse.movie.view.movies.TheaterBottomSheetDialogFragment
import woowacourse.movie.view.movies.adapter.MovieAdapter
import woowacourse.movie.view.reservation.detail.ReservationActivity

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

    override fun showMoviesScreen(
        movies: List<Movie>,
        navigate: (Movie) -> Unit,
    ) {
        val recyclerView: RecyclerView = binding.root.findViewById(R.id.recycler_view)
        val movieAdapter: MovieAdapter =
            MovieAdapter(
                object : OnMovieEventListener {
                    override fun onClickShowTheater(movie: Movie) {
                        navigate(movie)
                    }
                },
            )

        val movieItems = mutableListOf<MovieItem>()
        movies.forEachIndexed { index, movie ->
            movieItems.add(MovieItem.Movie(movie))
            if ((index + 1) % 3 == 0) {
                movieItems.add(MovieItem.Advertisement)
            }
        }
        recyclerView.adapter = movieAdapter
        movieAdapter.submitList(movieItems)
    }

    override fun showTheaterSelectDialog(
        movie: Movie,
        navigate: (Showings) -> Unit,
    ) {
        val dialog =
            TheaterBottomSheetDialogFragment.newInstance(
                movie,
                object : OnBottomSheetDialogEventListener {
                    override fun onClick(showings: Showings) {
                        navigateToReservation(movie, showings)
                    }
                },
            )
        dialog.show(childFragmentManager, "TheaterBottomSheetDialog")
    }

    override fun navigateToReservation(
        movie: Movie,
        showings: Showings,
    ) {
        val intent = ReservationActivity.newIntent(requireContext(), movie, showings)
        startActivity(intent)
    }
}
