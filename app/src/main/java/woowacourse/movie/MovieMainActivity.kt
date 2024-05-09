package woowacourse.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.home.view.MovieHomeFragment
import woowacourse.movie.reservationhistory.view.ReservationHistoryFragment
import woowacourse.movie.setting.view.SettingFragment
import woowacourse.movie.util.SharedPreferencesUtil

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding
    private val reservationHistoryFragment: ReservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val movieHomeFragment: MovieHomeFragment by lazy { MovieHomeFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation(savedInstanceState)

        sharedPreferences = SharedPreferencesUtil(this)
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            binding.bottomNavigationMain.selectedItemId = R.id.item_home
            addFragment(movieHomeFragment)
        }

        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_reservation_history -> {
                    replaceFragment(reservationHistoryFragment)
                    true
                }

                R.id.item_home -> {
                    replaceFragment(movieHomeFragment)
                    true
                }

                R.id.item_setting -> {
                    replaceFragment(settingFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_main_container, fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_main_container, fragment)
        }
    }

    companion object {
        lateinit var sharedPreferences: SharedPreferencesUtil
    }
}
