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
import woowacourse.movie.data.preferences.MoviePreferencesUtil
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.ui.history.MovieBookingHistoryFragment
import woowacourse.movie.ui.home.MovieHomeFragment
import woowacourse.movie.ui.notification.AlarmReceiver
import woowacourse.movie.ui.notification.NotificationContract.ACTION_NOTIFICATION
import woowacourse.movie.ui.notification.NotificationContract.KEY_RECEIVE_NOTIFICATION
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
            MoviePreferencesUtil(this).setBoolean(KEY_RECEIVE_NOTIFICATION, isGranted)
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)
        if (savedInstanceState == null) {
            replaceFragment(movieHomeFragment)
            binding.mainBottomNavigation.selectedItemId = R.id.menu_home
        }
        registerReceiver(
            alarmReceiver,
            IntentFilter().apply { addAction(ACTION_NOTIFICATION) },
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            handleNotificationRequestBySdkVersion()
        }
    }

    private fun handleNotificationRequestBySdkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                MoviePreferencesUtil(this).setBoolean(KEY_RECEIVE_NOTIFICATION, false)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            MoviePreferencesUtil(this).setBoolean(KEY_RECEIVE_NOTIFICATION, true)
        }
    }

    private fun initializeBottomNavigation() {
        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_booking_history -> replaceFragment(movieBookingHistoryFragment)
                R.id.menu_home -> replaceFragment(movieHomeFragment)
                R.id.menu_setting -> replaceFragment(movieSettingFragment)
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }
}
