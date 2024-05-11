package woowacourse.movie.ui.main

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.model.MoviePreferencesUtil
import woowacourse.movie.model.movie.AlarmReceiver
import woowacourse.movie.ui.history.MovieBookingHistoryFragment
import woowacourse.movie.ui.home.MovieHomeFragment
import woowacourse.movie.ui.setting.MovieSettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding
    private val movieBookingHistoryFragment: MovieBookingHistoryFragment by lazy { MovieBookingHistoryFragment() }
    private val movieHomeFragment: MovieHomeFragment by lazy { MovieHomeFragment() }
    private val movieSettingFragment: MovieSettingFragment by lazy { MovieSettingFragment() }
    private val alarmReceiver: AlarmReceiver by lazy { AlarmReceiver() }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            MoviePreferencesUtil(this).setBoolean("rcv_notification", isGranted)
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        if (savedInstanceState == null) {
            replace(movieHomeFragment)
            binding.mainBottomNavigation.selectedItemId = R.id.menu_home
        }

        registerReceiver(
            alarmReceiver,
            IntentFilter().apply { addAction("alert") },
            RECEIVER_NOT_EXPORTED,
        )

        requestNotificationPermission()
        initializeBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(alarmReceiver)
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    MoviePreferencesUtil(this).setBoolean("rcv_notification", false)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                MoviePreferencesUtil(this).setBoolean("rcv_notification", true)
            }
        }
    }

    private fun initializeBottomNavigation() {
        binding.mainBottomNavigation.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_booking_history -> replace(movieBookingHistoryFragment)
                    R.id.menu_home -> replace(movieHomeFragment)
                    R.id.menu_setting -> replace(movieSettingFragment)
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }
}
