package woowacourse.movie.view.movies

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMoviesBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.movies.cinema.CinemaSeclectionFragment

class MoviesActivity :
    BaseActivity<ActivityMoviesBinding>(R.layout.activity_movies),
    MoviesContract.View {
    private val presenter = MoviesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.loadData()
    }

    override fun showMovies(movies: List<MovieListItem>) {
        val lvMovie = findViewById<RecyclerView>(R.id.rv_movie)
        lvMovie.adapter =
            MovieListAdapter(
                movies,
                object : OnMovieEventListener {
                    override fun onReserveButtonClick(movie: Movie) {
                        val instance =
                            CinemaSeclectionFragment.newInstance(
                                movie.screening,
                            )
                        instance.show(supportFragmentManager, "CinemaSelectionFragment")
                    }
                },
            )
        lvMovie.layoutManager = LinearLayoutManager(this)
    }
}
