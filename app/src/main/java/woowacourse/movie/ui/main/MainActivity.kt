package woowacourse.movie.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.R
import woowacourse.movie.data.repository.SharedPreferenceRepository
import woowacourse.movie.data.repository.PreferenceRepository
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservationhistory.ReservationHistoryFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    private val reservationHistoryFragment: ReservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            addFirstFragment(reservationHistoryFragment)
        }
        initBottomNavigationView()
    }

    private fun addFirstFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment)
        }
    }

    private fun initBottomNavigationView() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_history -> replaceFragment(reservationHistoryFragment)

                R.id.screen_home -> replaceFragment(homeFragment)

                R.id.setting -> replaceFragment(settingFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }
}
