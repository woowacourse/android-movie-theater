package woowacourse.movie.view.movies

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMoviesBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.movies.cinema.CinemaSelectionFragment

class MoviesActivity :
    BaseActivity<ActivityMoviesBinding>(R.layout.activity_movies),
    MoviesContract.View {
    private val presenter = MoviesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        instance.show(supportFragmentManager, "CinemaSelectionFragment")
                    }
                },
            )
        binding.lvMovie.layoutManager = LinearLayoutManager(this)
    }
}
