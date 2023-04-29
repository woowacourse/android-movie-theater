package woowacourse.movie.movie.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.movie.fragment.HistoryFragment
import woowacourse.movie.movie.fragment.HomeFragment
import woowacourse.movie.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNotificationPermission()
        binding.navigationBar?.let { bindNavigationBar(it) }
    }

    private fun bindNavigationBar(bottomNav: BottomNavigationView) {
        bottomNav.run {
            setOnItemSelectedListener {
                onNavigationItemSelected(it)
                true
            }
            selectedItemId = R.id.home
        }
    }

    private fun onNavigationItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.history -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container_view, HistoryFragment())
                }
            }
            R.id.home -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container_view, HomeFragment())
                }
            }
            R.id.setting -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container_view, SettingFragment())
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
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
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
    }
}
