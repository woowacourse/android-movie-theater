package woowacourse.movie.activity

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import woowacourse.movie.NotificationPermission
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.fragment.BookHistoryFragment
import woowacourse.movie.fragment.HomeFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private val sharedPreference = SharedPreferenceUtil(this)
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, getString(R.string.notification_permission_is_granted), Toast.LENGTH_SHORT).show()
                sharedPreference.setSettingValue(SETTING_PUSH_ALARM_SWITCH_KEY, true)
            } else {
                Toast.makeText(this, getString(R.string.notification_permission_is_denied), Toast.LENGTH_SHORT).show()
                sharedPreference.setSettingValue(SETTING_PUSH_ALARM_SWITCH_KEY, false)
            }
        }

    private val bookHistoryFragment: Fragment by lazy { BookHistoryFragment() }
    private val homeFragment: Fragment by lazy { HomeFragment() }
    private val settingFragment: Fragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationPermission().requestNotificationPermission(this, ::getNotificationPermission)

        findViewById<BottomNavigationView>(R.id.navigation_main).setOnItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.framelayout_main, bookHistoryFragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_book_history -> return onChangedFragment(bookHistoryFragment)
            R.id.page_home -> return onChangedFragment(homeFragment)
            R.id.page_setting -> return onChangedFragment(settingFragment)
        }
        return false
    }

    private fun onChangedFragment(item: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout_main, item).commitAllowingStateLoss()
        return true
    }

    private fun getNotificationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            Toast.makeText(
                this,
                getString(R.string.if_permission_is_denied_cant_use_notification_service),
                Toast.LENGTH_LONG
            ).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    companion object {
        private const val SETTING_PUSH_ALARM_SWITCH_KEY = "settingPushAlarmSwitchKey"
    }
}
