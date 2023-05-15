package woowacourse.movie.view.activities.home

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.view.activities.home.fragments.reservationlist.ReservationListFragment
import woowacourse.movie.view.activities.home.fragments.screeninglist.ScreeningListFragment
import woowacourse.movie.view.activities.home.fragments.setting.SettingFragment

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNotificationPermission()

        initBottomNavigation()
    }

    private fun initNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_list_item -> changeFragment(ReservationListFragment())
                R.id.home_item -> changeFragment(ScreeningListFragment())
                R.id.setting_item -> changeFragment(SettingFragment())
            }
            return@setOnItemSelectedListener true
        }

        bottomNavigationView.selectedItemId = R.id.home_item
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
