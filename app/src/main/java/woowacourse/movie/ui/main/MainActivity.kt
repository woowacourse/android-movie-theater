package woowacourse.movie.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.NotificationChannelInfo
import woowacourse.movie.ui.NotificationGenerator
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
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                shortToast(getString(R.string.permission_denied))
            } else {
                settingSharedPreference.receivingPushAlarm = true
                shortToast(getString(R.string.permission_allowed))
            }
        }

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
            BOOKING_HISTORY_FRAGMENT -> fragmentMap[BOOKING_HISTORY_FRAGMENT] =
                BookingHistoryFragment()
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

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    companion object {
        private const val BOOKING_HISTORY_FRAGMENT = "booking_history_fragment"
        private const val HOME_FRAGMENT = "home_fragment"
        private const val SETTING_FRAGMENT = "setting_fragment"
    }
}
