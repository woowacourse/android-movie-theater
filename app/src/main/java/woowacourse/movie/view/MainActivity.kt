package woowacourse.movie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val reservationHistoryFragment = ReservationHistoryFragment()
    private val movieListFragment = MovieListFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.menu_home
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            return@setOnItemSelectedListener when (menu.itemId) {
                R.id.menu_list -> {
                    navigateToFragment(ReservationHistoryFragment())
                    true
                }

                R.id.menu_home -> {
                    navigateToFragment(MovieListFragment())
                    true
                }

                R.id.menu_settings -> {
                    navigateToFragment(SettingsFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_container_view, fragment)
        }
    }
}
