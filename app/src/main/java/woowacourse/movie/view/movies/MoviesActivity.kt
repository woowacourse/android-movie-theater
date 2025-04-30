package woowacourse.movie.view.movies

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieItem
import woowacourse.movie.domain.Showings
import woowacourse.movie.view.movies.adapter.MovieAdapter
import woowacourse.movie.view.reservation.detail.ReservationActivity

class MoviesActivity : AppCompatActivity(), MainContract.View {
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter.fetchData()
    }

    override fun showMoviesScreen(
        movies: List<Movie>,
        navigate: (Movie) -> Unit,
    ) {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
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
            TheaterBottomSheetDialogFragment(
                object : OnBottomSheetDialogEventListener {
                    override fun onClick(showings: Showings) {
                        navigateToReservation(movie, showings)
                    }
                },
            )
        val bundle = Bundle()
        bundle.putSerializable("movie", movie)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "TheaterBottomSheetDialog")
    }

    override fun navigateToReservation(
        movie: Movie,
        showings: Showings,
    ) {
        val intent = ReservationActivity.newIntent(this, movie, showings)
        startActivity(intent)
    }
}
