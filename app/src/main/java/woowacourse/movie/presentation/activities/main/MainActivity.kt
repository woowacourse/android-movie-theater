package woowacourse.movie.presentation.activities.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.history.HistoryFragment
import woowacourse.movie.presentation.activities.main.fragments.home.HomeFragment
import woowacourse.movie.presentation.activities.main.fragments.setting.SettingFragment
import woowacourse.movie.presentation.extensions.checkPermissions
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.extensions.replaceFragment
import woowacourse.movie.presentation.extensions.showToast
import woowacourse.movie.presentation.model.Reservation

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) showToast(getString(R.string.permission_allowed))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNotificationPermission()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.home
        bottomNavigationView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu -> replaceFragment(HistoryFragment.newInstance())
                R.id.home -> replaceFragment(HomeFragment.newInstance())
                R.id.setting -> replaceFragment(SettingFragment.newInstance())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        replaceFragment(R.id.fragment_container_view, fragment)
    }

    private fun requestNotificationPermission() {
        if (checkPermissions(Manifest.permission.POST_NOTIFICATIONS)) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getParcelableCompat<Reservation>(RESERVATION_KEY)?.let {
            replaceFragment(HistoryFragment.newInstance())
        }
    }

    companion object {
        private const val RESERVATION_KEY = "reservation_key"

        fun getIntent(context: Context, reservation: Reservation): Intent =
            Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra(RESERVATION_KEY, reservation)
    }
}
