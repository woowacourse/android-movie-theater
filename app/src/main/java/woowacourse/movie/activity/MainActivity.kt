package woowacourse.movie.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.fragment.HistoryFragment
import woowacourse.movie.fragment.HomeFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNotificationPermission()

        val bottomNav = findViewById<BottomNavigationView>(R.id.navigation_bar)
        bottomNav.run {
            setOnItemSelectedListener {
                when (it.itemId) {
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
                true
            }
            selectedItemId = R.id.home
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

    companion object {
        private const val MOVIE_KEY = "movie"
    }
}
