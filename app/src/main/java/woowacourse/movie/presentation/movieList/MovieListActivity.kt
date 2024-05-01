package woowacourse.movie.presentation.movieList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.movieList.adapter.MovieAdapter
import woowacourse.movie.repository.DummyTheaterList

class MovieListActivity : AppCompatActivity(), MovieListContract.View, MovieListClickListener {
    private val presenter = MovieListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        presenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        val movieList: RecyclerView = findViewById(R.id.rv_movies)
        movieList.adapter = MovieAdapter(movies, this)
    }

    override fun ticketingButtonClick(movieId: Long) {
        val bottomSheet = TheaterBottomSheetDialogFragment(DummyTheaterList.find(movieId), movieId)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}
