package woowacourse.movie.presentation.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTheaterBinding
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import woowacourse.movie.presentation.AlarmScheduler
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.view.history.historyList.ReservationHistoryFragment
import woowacourse.movie.presentation.view.history.historyList.ReservationHistoryRepository
import woowacourse.movie.presentation.view.home.movies.MoviesFragment
import woowacourse.movie.presentation.view.setting.SettingFragment
import java.time.LocalDateTime

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

        askPermissionNotification()
    }

    private fun askPermissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQ_PERMISSION_PUSH)
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

    companion object {
        private const val REQ_PERMISSION_PUSH = 1001
    }
}
