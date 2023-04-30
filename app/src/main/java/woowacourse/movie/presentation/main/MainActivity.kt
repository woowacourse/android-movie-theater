package woowacourse.movie.presentation.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.data.settings.SettingsNotificationData
import woowacourse.movie.presentation.bookedticketlist.BookedTicketsFragment
import woowacourse.movie.presentation.movielist.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment
import woowacourse.movie.presentation.util.replace

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        SettingsNotificationData.setNotification(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInitialFragment()
        initBottomNavigation()
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(this, ALARM_DENIED_TOAST_MESSAGE, Toast.LENGTH_LONG).show()
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
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
        private const val ALARM_DENIED_TOAST_MESSAGE = "알림거부 횟수가 2회 이상이라 앱 설정에서 직접 알림 설정을 켜야합니다."
    }
}
