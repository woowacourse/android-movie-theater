package woowacourse.movie.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import woowacourse.movie.BundleKeys
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.fragment.BookHistoryFragment
import woowacourse.movie.fragment.HomeFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNotificationPermission()

        findViewById<BottomNavigationView>(R.id.bnv_main).setOnItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.fl_main, BookHistoryFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_book_history -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_main, BookHistoryFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.page_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_main, HomeFragment())
                    .commitAllowingStateLoss()
                return true
            }
            R.id.page_setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_main, SettingFragment())
                    .commitAllowingStateLoss()
                return true
            }
        }

        return false
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // 권한 요청 거부한 경우
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "알림 권한을 받았습니다.", Toast.LENGTH_SHORT).show()
            SharedPreferenceUtil(this).setSettingValue(
                BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY,
                true
            )
        } else {
            Toast.makeText(this, "알림 권한을 획득하지 못하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
