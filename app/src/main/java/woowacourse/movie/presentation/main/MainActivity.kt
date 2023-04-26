package woowacourse.movie.presentation.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.bookedticketlist.BookedTicketsFragment
import woowacourse.movie.presentation.movielist.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment
import woowacourse.movie.presentation.util.replace

class MainActivity : AppCompatActivity() {

    // 알림 권한 설정관련 리펙토링 필요
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInitialFragment()
        initBottomNavigation()
        requestNotificationPermission()
    }

    // 알림 권한 설정관련 리펙토링 필요
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    private fun setInitialFragment() {
        this.replace<MovieListFragment>(R.id.main_fragment_container)
    }

    private fun initBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationMain)

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_booked_tickets -> {
                    this.replace<BookedTicketsFragment>(R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    this.replace<MovieListFragment>(R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.action_settings -> {
                    this.replace<SettingsFragment>(R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                else -> throw IllegalStateException(BOTTOM_NAVIGATION_WRONG_ITEM_ID_ERROR)
            }
        }

        bottomNavigation.selectedItemId = R.id.action_home
    }

    companion object {
        private const val BOTTOM_NAVIGATION_WRONG_ITEM_ID_ERROR = "설정된 아이템 제외한 아이템 아이디가 전달되었습니다."
    }
}
