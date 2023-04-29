package woowacourse.movie.view.moviemain

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.AlarmPreference
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment.Companion.IS_ALARM_ON

class MovieMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_main)

        requestNotificationPermission()

        val navigation = findViewById<BottomNavigationView>(R.id.navigation_view)

        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation_list -> {
                    replaceFragment<ReservationListFragment>()
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    replaceFragment<MovieListFragment>()
                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {
                    replaceFragment<SettingFragment>()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
        navigation.selectedItemId = R.id.action_home
    }

    private inline fun <reified T : Fragment> AppCompatActivity.replaceFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.fragment_container_view)
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        val sharedPreferences = AlarmPreference(this)
        if (isGranted) {
            sharedPreferences.putBoolean(IS_ALARM_ON, true)
        } else {
            sharedPreferences.putBoolean(IS_ALARM_ON, false)
        }
    }
}
