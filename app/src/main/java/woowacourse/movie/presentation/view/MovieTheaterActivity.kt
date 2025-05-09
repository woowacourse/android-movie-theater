package woowacourse.movie.presentation.view

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTheaterBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.view.history.ReservationHistoryFragment
import woowacourse.movie.presentation.view.home.movies.MoviesFragment
import woowacourse.movie.presentation.view.setting.SettingFragment

class MovieTheaterActivity : BaseActivity<ActivityMovieTheaterBinding>(R.layout.activity_movie_theater) {
    val homeFragment = MoviesFragment()
    val historyFragment = ReservationHistoryFragment()
    val settingFragment = SettingFragment()

    var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNavigationItemClickListener()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container_view, homeFragment)
                add(R.id.fragment_container_view, historyFragment)
                hide(historyFragment)
                add(R.id.fragment_container_view, settingFragment)
                hide(settingFragment)
            }
            binding.bottomNavigation.selectedItemId = R.id.menu_home
        }
    }

    private fun setBottomNavigationItemClickListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (isSameNavItem(item)) return@setOnItemSelectedListener false
            when (item.itemId) {
                R.id.menu_home -> navigateToScreen(homeFragment)
                R.id.menu_history -> navigateToScreen(historyFragment)
                R.id.menu_setting -> navigateToScreen(settingFragment)
            }
            true
        }
    }

    private fun isSameNavItem(item: MenuItem): Boolean = binding.bottomNavigation.selectedItemId == item.itemId

    private fun navigateToScreen(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            show(fragment)
            currentFragment?.let { hide(it) }
        }
        currentFragment = fragment
    }
}
