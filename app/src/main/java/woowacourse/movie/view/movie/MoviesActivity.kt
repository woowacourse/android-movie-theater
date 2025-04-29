package woowacourse.movie.view.movie

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.view.Extras
import woowacourse.movie.view.movie.adapter.MovieAdapter
import woowacourse.movie.view.reservation.reservation.ReservationActivity

class MoviesActivity :
    AppCompatActivity(),
    MovieContract.View {
    private val presenter: MoviePresenter by lazy { MoviePresenter(this) }
    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_movies, MoviesFragment())
            }
        }

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view).selectedItemId =
            R.id.fragment_movies

    }

    override fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    override fun navigateToReservation(movie: Movie) {
        val intent =
            Intent(
                this,
                ReservationActivity::class.java,
            ).apply { putExtra(Extras.MovieData.MOVIE_KEY, movie) }
        startActivity(intent)
    }
}
