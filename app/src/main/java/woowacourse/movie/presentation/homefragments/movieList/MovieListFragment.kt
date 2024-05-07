package woowacourse.movie.presentation.homefragments.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.adapter.MovieAdapter
import woowacourse.movie.presentation.homefragments.movieList.fragment.TheaterBottomDialogFragment
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener

class MovieListFragment : Fragment(), MovieListContract.View, MovieListClickListener {
    private val presenter: MovieListContract.Presenter = MovieListPresenter(this)
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        binding.rvMovies.adapter = MovieAdapter(movies, this)
    }

    override fun ticketingButtonClickListener(movieId: Long) {
        val bottomSheetFragment = TheaterBottomDialogFragment(movieId)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}
