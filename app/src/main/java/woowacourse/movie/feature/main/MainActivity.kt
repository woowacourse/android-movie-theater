package woowacourse.movie.feature.main

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.R.id.fcv_main
import woowacourse.movie.R.id.item_booking_history
import woowacourse.movie.R.id.item_home
import woowacourse.movie.R.id.item_setting
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.feature.bookinghistory.view.BookingHistoryFragment
import woowacourse.movie.feature.home.view.HomeFragment
import woowacourse.movie.feature.main.MainActivity.MainTab.entries
import woowacourse.movie.feature.setting.view.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
            }
        }

        setupNavigationItemSelectListener()
        setupNotificationPermissionHandler()
        binding.bottomNavMain.selectedItemId = item_home
    }

    private fun setupNavigationItemSelectListener() {
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            val tab = MainTab.from(item.itemId) ?: return@setOnItemSelectedListener false
            switchFragment(tab)
            true
        }
    }

    private fun switchFragment(tab: MainTab) {
        val currentFragment = supportFragmentManager.fragments.firstOrNull { it.isVisible }
        val targetFragment = supportFragmentManager.findFragmentByTag(tab.name)

        supportFragmentManager.commit {
            if (targetFragment == null) add(fcv_main, tab.fragment, null, tab.name)
            if (targetFragment != currentFragment) {
                currentFragment?.let { hide(it) }
                targetFragment?.let { show(it) }
            }
        }
    }

    private fun setupNotificationPermissionHandler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(POST_NOTIFICATIONS), REQUEST_CODE_NOTIFICATION_PERMISSION)
        }
    }

    enum class MainTab(
        @LayoutRes val id: Int,
        val fragment: Class<out Fragment>,
    ) {
        BOOKING_HISTORY(item_booking_history, BookingHistoryFragment::class.java),
        HOME(item_home, HomeFragment::class.java),
        SETTING(item_setting, SettingFragment::class.java),
        ;

        companion object {
            fun from(id: Int): MainTab? = entries.find { it.id == id }
        }
    }

    companion object {
        private const val REQUEST_CODE_NOTIFICATION_PERMISSION = 1
    }
}
