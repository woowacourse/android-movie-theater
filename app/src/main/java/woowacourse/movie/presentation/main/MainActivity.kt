package woowacourse.movie.presentation.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.model.data.local.SettingPreference
import woowacourse.movie.model.data.storage.SettingStorage
import woowacourse.movie.presentation.bookedticketlist.BookedTicketsFragment
import woowacourse.movie.presentation.movielist.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment
import woowacourse.movie.presentation.util.replace

class MainActivity : AppCompatActivity() {

    private lateinit var settingStorage: SettingStorage

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSettingStorage()
        initRequestPermissionLauncher()
        setInitialFragment()
        initBottomNavigation()
        requestNotificationPermission()
    }

    private fun initRequestPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            settingStorage.setNotificationSettings(isGranted)
        }
    }

    private fun initSettingStorage() {
        settingStorage = SettingPreference(this)
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
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

        bottomNavigation.selectedItemId = R.id.action_home

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_booked_tickets -> {
                    replaceFragment<BookedTicketsFragment>(bottomNavigation, it)
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    replaceFragment<MovieListFragment>(bottomNavigation, it)
                    return@setOnItemSelectedListener true
                }
                R.id.action_settings -> {
                    replaceFragment<SettingsFragment>(bottomNavigation, it)
                    return@setOnItemSelectedListener true
                }
                else -> throw IllegalStateException(BOTTOM_NAVIGATION_WRONG_ITEM_ID_ERROR)
            }
        }
    }

    private inline fun <reified T : Fragment> replaceFragment(
        bottomNavigation: BottomNavigationView,
        menuItem: MenuItem
    ) {
        if (bottomNavigation.selectedItemId != menuItem.itemId) {
            this.replace<T>(R.id.main_fragment_container)
        }
    }

    companion object {
        private const val BOTTOM_NAVIGATION_WRONG_ITEM_ID_ERROR = "설정된 아이템 제외한 아이템 아이디가 전달되었습니다."
        private const val ALARM_DENIED_TOAST_MESSAGE = "알림거부 횟수가 2회 이상이라 앱 설정에서 직접 알림 설정을 켜야합니다."
    }
}
