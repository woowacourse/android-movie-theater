package woowacourse.movie.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.fragment.MoviesFragment
import woowacourse.movie.fragment.ReservationListFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(ReservationListFragment())
        setOnBottomNavigationClickListener()
        requestNotificationPermission()
    }

    private fun setOnBottomNavigationClickListener() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bottom_navigation -> replaceFragment(MoviesFragment())
                R.id.setting_bottom_navigation -> replaceFragment(SettingFragment())
                R.id.reservation_list_bottom_navigation -> replaceFragment(ReservationListFragment())
            }
            true
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.main_fragment, fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment, fragment)
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                } else { requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }
            } else { }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
    }
}
