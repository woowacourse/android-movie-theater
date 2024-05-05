package woowacourse.movie.presentation.homefragments.movieList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.adapter.MovieAdapter
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener
import woowacourse.movie.repository.DummyTheaterList

class MovieListFragment : Fragment(), MovieListContract.View, MovieListClickListener {
    private val presenter: MovieListContract.Presenter = MovieListPresenter(this)
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        // Log.d("MovieListFragment", "onCreateView")
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        // Log.d("MovieListFragment", "onViewCreated")
        presenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        binding.rvMovies.adapter = MovieAdapter(movies, this)
    }

    override fun ticketingButtonClickListener(movieId: Long) {
        presenter.onTicketingButtonClick(DummyTheaterList, movieId)
    }

    override fun showBottomSheetFragment(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    override fun onStart() {
        super.onStart()
        Log.d("MovieListFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MovieListFragment", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MovieListFragment", "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MovieListFragment", "onPause")
    }
}
