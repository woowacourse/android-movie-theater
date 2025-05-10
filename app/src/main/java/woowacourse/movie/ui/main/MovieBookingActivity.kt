package woowacourse.movie.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieBookingBinding
import woowacourse.movie.ui.history.view.BookingHistoryFragment
import woowacourse.movie.ui.movielist.view.MovieListFragment
import woowacourse.movie.ui.settings.view.SettingsFragment
import woowacourse.movie.utils.Destination

class MovieBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding =
            DataBindingUtil.setContentView(
                this@MovieBookingActivity,
                R.layout.activity_movie_booking,
            )

        applyWindowInsets()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<MovieListFragment>(R.id.main_fragment_container_view)
                binding.navigation.selectedItemId = R.id.navigation_home
            }
        } else {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.main_fragment_container_view)
            when (currentFragment) {
                is MovieListFragment -> binding.navigation.selectedItemId = R.id.navigation_home
                is SettingsFragment -> binding.navigation.selectedItemId = R.id.navigation_settings
                is BookingHistoryFragment ->
                    binding.navigation.selectedItemId =
                        R.id.navigation_history
            }
        }
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MovieListFragment>(R.id.main_fragment_container_view)
                    }
                    true
                }

                R.id.navigation_history -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<BookingHistoryFragment>(R.id.main_fragment_container_view)
                    }
                    true
                }

                R.id.navigation_settings -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SettingsFragment>(R.id.main_fragment_container_view)
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val EXTRA_DESTINATION = "EXTRA_DESTINATION"

        fun newIntent(
            context: Context,
            destination: Destination,
        ): Intent =
            Intent(context, MovieBookingActivity::class.java).apply {
                putExtra(EXTRA_DESTINATION, destination)
            }
    }
}
