package woowacourse.movie.view.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMoviesBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.view.Extras
import woowacourse.movie.view.movie.adapter.MovieAdapter
import woowacourse.movie.view.reservation.reservation.ReservationFragment

class MoviesActivity :
    AppCompatActivity(),
    MovieContract.View {
    private val bnView: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation_view) }
    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMoviesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movies)
        binding.main = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fcv_main, MoviesFragment())
            }
        }

        bnView.selectedItemId = R.id.fragment_movies
        bnView.setOnItemSelectedListener {
            val fragment =
                when (it.itemId) {
                    R.id.fragment_movies -> MoviesFragment()
                    R.id.fragment_list -> ReservationListFragment()
                    R.id.fragment_setting -> SettingFragment()
                    else -> throw IllegalArgumentException("알 수 없는 프래그먼트 입니다")
                }

            replaceFragment(fragment)
            true
        }
    }

    override fun showMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    override fun navigateToReservation(movie: Movie) {
        val intent =
            Intent(
                this,
                ReservationFragment::class.java,
            ).apply { putExtra(Extras.MovieData.MOVIE_KEY, movie) }
        startActivity(intent)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main, fragment)
            addToBackStack(null)
        }
    }

    fun showBottomNav(isVisible: Boolean) {
        val nav = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        nav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
