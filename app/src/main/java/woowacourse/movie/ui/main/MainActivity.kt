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
import woowacourse.movie.permission.PermissionLauncher
import woowacourse.movie.permission.PermissionLauncher.getPermissionLauncher
import woowacourse.movie.util.SettingSharedPreference
import woowacourse.movie.util.shortToast

class MainActivity : AppCompatActivity() {

    private val settingSharedPreference: SettingSharedPreference by lazy {
        SettingSharedPreference(this)
    }
    private val fragmentMap = mutableMapOf<String, Fragment?>(
        BOOKING_HISTORY_FRAGMENT to null,
        HOME_FRAGMENT to null,
        SETTING_FRAGMENT to null
    )
    private val notificationGenerator: NotificationGenerator by lazy {
        NotificationGenerator(this)
    }
    private val permissionLauncher: ActivityResultLauncher<String> by lazy {
        getPermissionLauncher(
            deniedCase = {
                settingSharedPreference.receivingPushAlarm = false
                shortToast(getString(R.string.permission_denied))
            },
            allowedCase = {
                settingSharedPreference.receivingPushAlarm = true
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
        notificationGenerator.createChannel(NotificationChannelInfo.BOOKING_ALARM)
    }

    private fun initNavigation() {
        val navigation = findViewById<BottomNavigationView>(R.id.main_bottom_navi)

        navigation.selectedItemId = R.id.action_menu_home
        navigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_booking_history ->
                    return@setOnItemSelectedListener replaceFragment(BOOKING_HISTORY_FRAGMENT)
                R.id.action_menu_home ->
                    return@setOnItemSelectedListener replaceFragment(HOME_FRAGMENT)
                R.id.action_menu_setting ->
                    return@setOnItemSelectedListener replaceFragment(SETTING_FRAGMENT)
            }
            return@setOnItemSelectedListener false
        }
    }

    private fun replaceFragment(tag: String): Boolean {
        supportFragmentManager.findFragmentByTag(tag) ?: when (tag) {
            BOOKING_HISTORY_FRAGMENT -> fragmentMap[BOOKING_HISTORY_FRAGMENT] = BookingHistoryFragment()
            HOME_FRAGMENT -> fragmentMap[HOME_FRAGMENT] = HomeFragment()
            SETTING_FRAGMENT -> fragmentMap[SETTING_FRAGMENT] = SettingFragment()
            else -> return false
        }

        fragmentMap[tag]?.let {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainerView, it, tag)
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        PermissionLauncher.requestPermission(
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
