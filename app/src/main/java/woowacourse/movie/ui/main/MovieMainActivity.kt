package woowacourse.movie.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.ui.booking.MovieBookingHistoryFragment
import woowacourse.movie.ui.home.MovieHomeFragment
import woowacourse.movie.ui.setting.MovieSettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding

    private val homeFragment by lazy { MovieHomeFragment() }
    private val bookingHistoryFragment by lazy { MovieBookingHistoryFragment() }
    private val settingFragment by lazy { MovieSettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        if (savedInstanceState == null) {
            replace(homeFragment)
            binding.mainBottomNavigation.selectedItemId = R.id.menu_home
        }

        initializeBottomNavigation()
        requestNotificationPermission()
    }

    private fun initializeBottomNavigation() {
        binding.mainBottomNavigation.apply {
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_booking_history -> replace(bookingHistoryFragment)
                    R.id.menu_home -> replace(homeFragment)
                    R.id.menu_setting -> replace(settingFragment)
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, fragment, fragment.tag)
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(
                        this,
                        getString(R.string.alarm_permission_fail_comment),
                        Toast.LENGTH_SHORT,
                    )
                        .show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) {}

    companion object {
        fun startActivity(context: Context) =
            Intent(context, MovieMainActivity::class.java).run {
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                context.startActivity(this)
            }
    }
}
