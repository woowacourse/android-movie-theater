package woowacourse.movie.view.movies

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.adapter.MovieAdapter
import woowacourse.movie.view.movies.bottomsheet.TheaterBottomSheet
import woowacourse.movie.view.movies.model.UiModel

class MovieListActivity : AppCompatActivity(), MovieListContract.View {
    private val presenter: MovieListContract.Presenter by lazy {
        MovieListPresenter(this, MovieStore(), TheaterStore())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_list)
        initView()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter.loadUiData()
    }

    override fun showMovieList(movieList: List<UiModel>) {
        val rv = findViewById<RecyclerView>(R.id.rv)
        val adapter =
            MovieAdapter(
                itemsList = movieList,
                onClickBooking = {
                    presenter.loadTheaters(it)
                },
            )
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun showTheaterBottomSheet(
        movieId: Int,
        theaters: Theaters,
    ) {
        TheaterBottomSheet(
            theaters,
            movieId,
            onclick = {},
        ).show(supportFragmentManager, THEATER_BOTTOM_SHEET)
    }

    companion object {
        private const val THEATER_BOTTOM_SHEET = "BOTTOM_SHEET"
    }
}
