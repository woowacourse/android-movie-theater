package woowacourse.movie.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieBookingBinding
import woowacourse.movie.ui.history.view.BookingHistoryFragment
import woowacourse.movie.ui.movielist.view.MovieListFragment
import woowacourse.movie.ui.settings.view.SettingsFragment

class MovieBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBookingBinding
    private val homeFragment by lazy { MovieListFragment() }
    private val settingFragment by lazy { SettingsFragment() }
    private val historyFragment by lazy { BookingHistoryFragment() }

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
                replace(R.id.main_fragment_container_view, homeFragment)
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
                        replace(R.id.main_fragment_container_view, homeFragment)
                    }
                    true
                }

                R.id.navigation_history -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.main_fragment_container_view, historyFragment)
                    }
                    true
                }

                R.id.navigation_settings -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.main_fragment_container_view, settingFragment)
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
}
