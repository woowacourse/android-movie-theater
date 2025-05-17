package woowacourse.movie.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieBookingBinding
import woowacourse.movie.providers.StorageProvider
import woowacourse.movie.ui.history.BookingHistoryFragment
import woowacourse.movie.ui.movielist.view.MovieListFragment
import woowacourse.movie.ui.settings.view.SettingsFragment

class MovieBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_booking)
        applyWindowInsets()
        initBottomNavigationListener()
        requestPostNotificationPermission()

        if (savedInstanceState == null) {
            binding.navigation.selectedItemId = R.id.navigation_home
        } else {
            updateBottomNavigation()
        }
    }

    private fun initBottomNavigationListener() {
        binding.navigation.setOnItemSelectedListener { item ->
            if (binding.navigation.selectedItemId == item.itemId) return@setOnItemSelectedListener false

            when (item.itemId) {
                R.id.navigation_home -> {
                    attachHomeFragment()
                    true
                }

                R.id.navigation_history -> {
                    attachHistoryFragment()
                    true
                }

                R.id.navigation_settings -> {
                    attachSettingFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun requestPostNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasGranted = hasPermission(Manifest.permission.POST_NOTIFICATIONS)
            if (hasGranted) return

            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPushNotificationRecommend()
                return
            }

            if (StorageProvider.isFirstPostNotificationPermissionRequest) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                StorageProvider.setFirstPostNotificationPermissionRequestState(false)
            }
        }
    }

    private fun hasPermission(permissionName: String): Boolean =
        ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                showPushNotificationSuccess()
                return@registerForActivityResult
            }
            showPushNotificationRecommend()
        }

    private fun showPushNotificationSuccess() {
        Toast.makeText(
            this, getString(R.string.push_notification_success), Toast.LENGTH_LONG
        ).show()
    }

    private fun showPushNotificationRecommend() {
        Toast.makeText(
            this, getString(R.string.recommend_push_notification), Toast.LENGTH_LONG
        ).show()
    }

    private fun attachHomeFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MovieListFragment>(R.id.main_fragment_container_view)
        }
    }

    private fun attachHistoryFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<BookingHistoryFragment>(R.id.main_fragment_container_view)
        }
    }

    private fun attachSettingFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SettingsFragment>(R.id.main_fragment_container_view)
        }
    }

    private fun updateBottomNavigation() {
        val activeFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container_view)
                ?: MovieListFragment()
        when (activeFragment) {
            is MovieListFragment -> binding.navigation.selectedItemId = R.id.navigation_home
            is SettingsFragment -> binding.navigation.selectedItemId = R.id.navigation_settings
            is BookingHistoryFragment -> binding.navigation.selectedItemId = R.id.navigation_history
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
