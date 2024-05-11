package woowacourse.movie.list.view

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.putBoolean("notification", isGranted).apply()
            Log.d("alsong", "requestNotificationPermission: $isGranted")
        }
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = getSharedPreferences("settings", MODE_PRIVATE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.home = this
        setContentView(binding.root)
        requestNotificationPermission()
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNav.selectedItemId = R.id.home_fragment_item
        showFragment(MovieListFragment())
        binding.bottomNav.setOnItemSelectedListener { item ->
            convertFragment(item.itemId)
            true
        }
    }

    private fun convertFragment(itemId: Int) {
        when (itemId) {
            R.id.home_fragment_item -> showFragment(MovieListFragment())
            R.id.reservation_details_fragment_item -> showFragment(ReservationHistoryFragment())
            R.id.setting_fragment_item -> showFragment(SettingFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
    }

    private fun requestNotificationPermission() {
        // 권한이 허용되지 않았으면 이 if문에 들어감
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // 이전에 권한 요청 거부했다면 이쪽으로 빠짐
                } else {
                    // 처음에 권한 요청을 할 때에는 무조건 이쪽으로 빠짐
                    // 알림을 요청함. 동시에 requestPermissionLauncher의 람다함수를 실행한다.
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }
}
