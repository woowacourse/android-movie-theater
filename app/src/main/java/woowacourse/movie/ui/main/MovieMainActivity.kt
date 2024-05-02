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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        initializeBottomNavigation()
    }

    private fun initializeBottomNavigation() {
        replace(MovieHomeFragment())
        binding.mainBottomNavigation.apply {
            selectedItemId = R.id.menu_home
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_booking_history -> replace(MovieBookingHistoryFragment())
                    R.id.menu_home -> replace(MovieHomeFragment())
                    R.id.menu_setting -> replace(MovieSettingFragment())
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
