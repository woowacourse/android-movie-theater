package woowacourse.movie.view.movies

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.movies.cinema.CinemaSeclectionFragment

class MoviesActivity :
    BaseActivity(R.layout.activity_movies),
    MoviesContract.View {
    private val presenter = MoviesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.loadData()
    }

    override fun showMovies(movies: List<MovieListItem>) {
        val lvMovie = findViewById<RecyclerView>(R.id.lv_movie)
        lvMovie.adapter =
            MovieListAdapter(
                movies,
                object : OnMovieEventListener {
                    override fun onReserveButtonClick(movie: Movie) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add(R.id.fragment_container_view, CinemaSeclectionFragment.newInstance(movie.screening))
                        }
                    }
                },
            )
        lvMovie.layoutManager = LinearLayoutManager(this)
    }
}
