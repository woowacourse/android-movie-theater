package woowacourse.movie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.ui.history.MovieBookingHistoryFragment
import woowacourse.movie.ui.home.MovieHomeFragment
import woowacourse.movie.ui.setting.MovieSettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding
    private val movieBookingHistoryFragment: MovieBookingHistoryFragment by lazy { MovieBookingHistoryFragment() }
    private val movieHomeFragment: MovieHomeFragment by lazy { MovieHomeFragment() }
    private val movieSettingFragment: MovieSettingFragment by lazy { MovieSettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        if (savedInstanceState == null) {
            replace(movieHomeFragment)
            binding.mainBottomNavigation.selectedItemId = R.id.menu_home
        }

        initializeBottomNavigation()
    }

    private fun initializeBottomNavigation() {
        binding.mainBottomNavigation.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_booking_history -> replace(movieBookingHistoryFragment)
                    R.id.menu_home -> replace(movieHomeFragment)
                    R.id.menu_setting -> replace(movieSettingFragment)
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }
}
