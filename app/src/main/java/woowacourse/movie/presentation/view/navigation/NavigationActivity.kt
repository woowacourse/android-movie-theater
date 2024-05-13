package woowacourse.movie.presentation.view.navigation

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityNavigationBinding
import woowacourse.movie.presentation.view.reservation.history.ReservationListFragment
import woowacourse.movie.presentation.view.screening.ScreeningMovieFragment
import woowacourse.movie.presentation.view.setting.SettingFragment
import woowacourse.movie.utils.SharedPrefs
import woowacourse.movie.utils.versionTiramisuOrHigher

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding
    private val screeningMovieFragment: ScreeningMovieFragment by lazy { ScreeningMovieFragment() }
    private val reservationListFragment: ReservationListFragment by lazy { ReservationListFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefs = SharedPrefs(this)
        requestNotificationPermission()

        setBottomNavigationView()
        if (savedInstanceState == null) {
            binding.navigationView.selectedItemId = R.id.home_fragment
        }
    }

    private fun setBottomNavigationView() {
        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> {
                    replaceFragment(screeningMovieFragment)
                    true
                }

                R.id.setting_fragment -> {
                    replaceFragment(settingFragment)
                    true
                }

                R.id.reservation_list_fragment -> {
                    replaceFragment(reservationListFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.selected_fragment, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (versionTiramisuOrHigher()) {
                if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            showNotificationGrantedStatus(isGranted)
        }

    private fun showNotificationGrantedStatus(isGranted: Boolean) {
        when (isGranted) {
            true -> {
                showPermissionMessage(getString(R.string.agree_notification_permission))
            }

            false -> {
                showPermissionMessage(getString(R.string.disagree_notification_permission))
            }
        }
    }

    private fun showPermissionMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
