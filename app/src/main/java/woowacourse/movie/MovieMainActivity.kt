package woowacourse.movie

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.MovieApplication.Companion.sharedPrefs
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.home.view.MovieHomeFragment
import woowacourse.movie.reservationhistory.view.ReservationHistoryFragment
import woowacourse.movie.setting.view.SettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding
    private val reservationHistoryFragment: ReservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val movieHomeFragment: MovieHomeFragment by lazy { MovieHomeFragment() }
    private var settingFragment: SettingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        requestNotificationPermission()
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            binding.bottomNavigationMain.selectedItemId = R.id.item_home
            addFragment(movieHomeFragment)
        }

        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_reservation_history -> {
                    replaceFragment(reservationHistoryFragment)
                    true
                }

                R.id.item_home -> {
                    replaceFragment(movieHomeFragment)
                    true
                }

                R.id.item_setting -> {
                    replaceFragment(settingFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_main_container, fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_main_container, fragment)
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!shouldShowRequestPermissionRationale(permission.POST_NOTIFICATIONS)) {
                    requestPermissionLauncher.launch(permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            sharedPrefs.saveAlarmSetting(isGranted)
        }
}
