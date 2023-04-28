package woowacourse.movie

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.view.main.booklist.BookListFragment
import woowacourse.movie.presentation.view.main.home.MovieListFragment
import woowacourse.movie.presentation.view.main.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bottomNavigation : BottomNavigationView by lazy { findViewById(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNotificationPermission()
        initBottomNavigation()
        setSelectEvent()
    }

    private fun setSelectEvent() {
        bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener setBottomNavigationClickEvent(it)
        }
    }

    private fun setBottomNavigationClickEvent(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.action_book_list -> changeFragment(BookListFragment())
            R.id.action_home -> changeFragment(MovieListFragment())
            R.id.action_settings -> changeFragment(SettingFragment())
            else -> return false
        }
        return true
    }

    private fun initBottomNavigation() {
        changeFragment(MovieListFragment())
        bottomNavigation.selectedItemId = R.id.action_home
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment).commit()
    }

    private fun requestNotificationPermission() {
        Application.prefs.setBoolean(getString(R.string.push_alarm_permission), false)

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
        Application.prefs.setBoolean(getString(R.string.push_alarm_permission), isGranted)
    }
}
