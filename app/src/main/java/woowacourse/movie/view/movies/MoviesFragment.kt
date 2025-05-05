package woowacourse.movie.view.movies

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.base.BaseFragment
import woowacourse.movie.view.movies.cinema.CinemaSelectionFragment

class MoviesFragment :
    BaseFragment<FragmentMoviesBinding>(R.layout.fragment_movies),
    MoviesContract.View {
    private val presenter = MoviesPresenter(this)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData()
    }

    override fun showMovies(movies: List<MovieListItem>) {
        binding.lvMovie.adapter =
            MovieListAdapter(
                movies,
                object : OnMovieEventListener {
                    override fun onReserveButtonClick(movie: Movie) {
                        val instance =
                            CinemaSelectionFragment.newInstance(
                                movie.screening,
                            )
                        instance.show(parentFragmentManager, "CinemaSelectionFragment")
                    }
                },
            )
        binding.lvMovie.layoutManager = LinearLayoutManager(context)
    }
}
