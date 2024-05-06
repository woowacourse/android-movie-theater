package woowacourse.movie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.ui.booking.MovieBookingHistoryFragment
import woowacourse.movie.ui.home.MovieHomeFragment
import woowacourse.movie.ui.setting.MovieSettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding

    private val homeFragment by lazy { MovieHomeFragment() }
    private val bookingHistoryFragment by lazy { MovieBookingHistoryFragment() }
    private val settingFragment by lazy { MovieSettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        initializeBottomNavigation()
    }

    private fun initializeBottomNavigation() {
        replace(homeFragment)

        binding.mainBottomNavigation.apply {
            selectedItemId = R.id.menu_home
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_booking_history -> replace(bookingHistoryFragment)
                    R.id.menu_home -> replace(homeFragment)
                    R.id.menu_setting -> replace(settingFragment)
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, fragment, fragment.tag)
        }
    }
}
