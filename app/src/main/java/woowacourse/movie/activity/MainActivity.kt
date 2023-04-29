package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY
import woowacourse.movie.PermissionManager
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.fragment.BookHistoryFragment
import woowacourse.movie.fragment.HomeFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            progressIsGrantedNotificationPermission()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionManager.requestNotificationPermission(this, this, requestPermissionLauncher)
        findViewById<BottomNavigationView>(R.id.bnv_main).setOnItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.fl_main, BookHistoryFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_book_history -> {
                replaceFragment(BookHistoryFragment())
                return true
            }
            R.id.page_home -> {
                replaceFragment(HomeFragment())
                return true
            }
            R.id.page_setting -> {
                replaceFragment(SettingFragment())
                return true
            }
        }
        return false
    }

    private fun progressIsGrantedNotificationPermission() = { isGranted: Boolean ->
        Toast.makeText(
            this,
            if (isGranted) getString(R.string.notification_permission_is_granted) else
                getString(R.string.notification_permission_is_denied),
            Toast.LENGTH_SHORT
        ).show()
        SharedPreferenceUtil.setBooleanValue(this, SETTING_PUSH_ALARM_SWITCH_KEY, isGranted)
        updatePushAlarmSwitch(isGranted)
    }

    private fun updatePushAlarmSwitch(wantChecked: Boolean) {
        supportFragmentManager.findFragmentById(R.id.fl_main)?.view?.findViewById<SwitchMaterial>(
            R.id.sw_setting_can_push
        )?.isChecked = wantChecked
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, fragment).commitAllowingStateLoss()
    }
}
