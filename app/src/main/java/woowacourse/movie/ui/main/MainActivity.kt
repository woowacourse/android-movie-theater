package woowacourse.movie.ui.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.notification.NotificationChannelInfo
import woowacourse.movie.notification.NotificationGenerator
import woowacourse.movie.permission.getPermissionLauncher
import woowacourse.movie.permission.requestPermission
import woowacourse.movie.ui.bookinghistory.BookingHistoryFragment
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.setting.SettingFragment
import woowacourse.movie.ui.setting.SettingSharedPreference
import woowacourse.movie.util.shortToast

class MainActivity : AppCompatActivity() {

    private val settingSharedPreference: SettingSharedPreference by lazy {
        SettingSharedPreference(this)
    }

    private val permissionLauncher: ActivityResultLauncher<String> by lazy {
        getPermissionLauncher(
            deniedCase = {
                settingSharedPreference.isAvailableAlarm = false
                shortToast(getString(R.string.permission_denied))
            },
            allowedCase = {
                settingSharedPreference.isAvailableAlarm = true
                shortToast(getString(R.string.permission_allowed))
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
        requestNotificationPermission()
        NotificationGenerator.createChannel(this, NotificationChannelInfo.BOOKING_ALARM)
    }

    private fun initNavigation() {
        val navigation = findViewById<BottomNavigationView>(R.id.main_bottom_navi)

        navigation.selectedItemId = R.id.action_menu_home
        navigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_booking_history -> changeFragment(BOOKING_HISTORY_FRAGMENT)
                R.id.action_menu_home -> changeFragment(HOME_FRAGMENT)
                R.id.action_menu_setting -> changeFragment(SETTING_FRAGMENT)
                else -> return@setOnItemSelectedListener false
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun changeFragment(tag: String): Boolean {
        val fragment = findFragment(tag) ?: return false

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, fragment, tag)
        }
        return true
    }

    private fun findFragment(tag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag) ?: when (tag) {
            BOOKING_HISTORY_FRAGMENT -> BookingHistoryFragment()
            HOME_FRAGMENT -> HomeFragment()
            SETTING_FRAGMENT -> SettingFragment()
            else -> null
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestPermission(
            context = this,
            launcher = permissionLauncher,
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
    }

    companion object {
        private const val BOOKING_HISTORY_FRAGMENT = "booking_history_fragment"
        private const val HOME_FRAGMENT = "home_fragment"
        private const val SETTING_FRAGMENT = "setting_fragment"
    }
}
