package woowacourse.movie.view.moviemain

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.AlarmPreference
import woowacourse.movie.R
import woowacourse.movie.util.requestRequiredPermissions
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment

class MovieMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_main)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation_view)

        navigation.setOnItemSelectedListener { item ->
            setMenuItemClickListener(item)
        }
        navigation.selectedItemId = R.id.action_home

        requestPermissions()
    }

    private fun setMenuItemClickListener(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_reservation_list -> {
            replaceFragment<ReservationListFragment>()
            true
        }
        R.id.action_home -> {
            replaceFragment<MovieListFragment>()
            true
        }
        R.id.action_setting -> {
            replaceFragment<SettingFragment>()
            true
        }
        else -> false
    }

    private inline fun <reified T : Fragment> AppCompatActivity.replaceFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.fragment_container_view)
        }
    }

    private fun requestPermissions() {
        val permissionsRequired = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissionsRequired.add(Manifest.permission.POST_NOTIFICATIONS)
        this.requestRequiredPermissions(permissionsRequired, requestPermissionLauncher::launch)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        val alarmPreferences = AlarmPreference.getInstance(applicationContext)
        if (isGranted) {
            alarmPreferences.setIsAlarmOn(true)
        } else {
            alarmPreferences.setIsAlarmOn(false)
        }
    }
}
