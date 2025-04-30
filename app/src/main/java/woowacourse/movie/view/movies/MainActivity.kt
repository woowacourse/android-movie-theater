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
import woowacourse.movie.domain.Theater
import woowacourse.movie.view.movies.adapter.MovieAdapter
import woowacourse.movie.view.reservation.detail.ReservationActivity

class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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
        navigate: (Theater) -> Unit,
    ) {
        val dialog =
            TheaterBottomSheetDialogFragment(
                object : OnBottomSheetDialogEventListener {
                    override fun onClick(theater: Theater) {
                        navigateToReservation(theater)
                    }
                },
            )
        dialog.show(supportFragmentManager, "TheaterBottomSheetDialog")
    }

    override fun navigateToReservation(theater: Theater) {
        val intent = ReservationActivity.newIntent(this, theater)
        startActivity(intent)
    }
}
