package woowacourse.movie.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.main.presenter.MovieMainPresenter
import woowacourse.movie.main.presenter.contract.MovieMainContract
import woowacourse.movie.main.view.adapter.movie.MovieAdapter
import woowacourse.movie.model.Movie
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class MovieMainActivity : AppCompatActivity(), MovieMainContract.View {
    private lateinit var movieRecyclerView: RecyclerView

    private lateinit var movieMainPresenter: MovieMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_main)

        movieMainPresenter = MovieMainPresenter(this)
        movieMainPresenter.loadMovies()
    }

    override fun displayMovies(movies: List<Movie>) {
        movieRecyclerView = findViewById(R.id.movieRecyclerView)
        movieRecyclerView.adapter = MovieAdapter(movies, ::displayTheaterSelectionDialog)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun displayTheaterSelectionDialog(id: Long) {
        val bundle = Bundle()
        bundle.putLong(KEY_MOVIE_ID, id)
        val theaterSelectionFragment = TheaterSelectionFragment()
        theaterSelectionFragment.arguments = bundle
        theaterSelectionFragment.show(supportFragmentManager, theaterSelectionFragment.tag)
    }
}
