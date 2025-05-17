package woowacourse.movie.ui

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

class MovieBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_booking)
        applyWindowInsets()

        initBottomNavigationListener()

        if (savedInstanceState == null) {
            binding.navigation.selectedItemId = R.id.navigation_home
        } else {
            updateBottomNavigation()
        }
    }

    private fun initBottomNavigationListener() {
        binding.navigation.setOnItemSelectedListener { item ->
            if (binding.navigation.selectedItemId == item.itemId) return@setOnItemSelectedListener false

            when (item.itemId) {
                R.id.navigation_home -> {
                    attachHomeFragment()
                    true
                }

                R.id.navigation_history -> {
                    attachHistoryFragment()
                    true
                }

                R.id.navigation_settings -> {
                    attachSettingFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun attachHomeFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MovieListFragment>(R.id.main_fragment_container_view)
        }
    }

    private fun attachHistoryFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<BookingHistoryFragment>(R.id.main_fragment_container_view)
        }
    }

    private fun attachSettingFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SettingsFragment>(R.id.main_fragment_container_view)
        }
    }

    private fun updateBottomNavigation() {
        val activeFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container_view) ?: MovieListFragment()
        when (activeFragment) {
            is MovieListFragment -> binding.navigation.selectedItemId = R.id.navigation_home
            is SettingsFragment -> binding.navigation.selectedItemId = R.id.navigation_settings
            is BookingHistoryFragment -> binding.navigation.selectedItemId = R.id.navigation_history
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
