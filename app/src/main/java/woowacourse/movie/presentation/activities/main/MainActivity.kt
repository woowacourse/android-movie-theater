package woowacourse.movie.presentation.activities.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.history.HistoryFragment
import woowacourse.movie.presentation.activities.main.fragments.home.HomeFragment
import woowacourse.movie.presentation.activities.main.fragments.setting.SettingFragment
import woowacourse.movie.presentation.extensions.checkPermissions
import woowacourse.movie.presentation.extensions.showToast

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) showToast(getString(R.string.permission_allowed))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNotificationPermission()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.home
        bottomNavigationView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.history -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, HistoryFragment.getInstance())
                    }
                }

                R.id.home -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, HomeFragment.getInstance())
                    }
                }

                R.id.setting -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, SettingFragment.getInstance())
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun requestNotificationPermission() {
        if (checkPermissions(Manifest.permission.POST_NOTIFICATIONS)) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
