package woowacourse.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.reservationDetails.ReservationDetailsFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment by lazy { HomeFragment().newInstance() }
    private val settingFragment by lazy { SettingFragment().newInstance() }
    private val reservationDetailsFragment by lazy { ReservationDetailsFragment().newInstance() }
    private var activeFragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportFragmentManager.commit {
            add(R.id.main_fragment_container, homeFragment)
            add(R.id.main_fragment_container, settingFragment)
                .hide(settingFragment)
            add(R.id.main_fragment_container, reservationDetailsFragment)
                .hide(reservationDetailsFragment)
        }

        binding.mainBottomNavigationBar.selectedItemId = R.id.bottom_navigation_home
        binding.mainBottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_reservation_details ->
                    switchFragment(reservationDetailsFragment)

                R.id.bottom_navigation_home ->
                    switchFragment(homeFragment)

                R.id.bottom_navigation_setting ->
                    switchFragment(settingFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun switchFragment(selectedFragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            if (selectedFragment != activeFragment) {
                hide(activeFragment)
                show(selectedFragment)
                activeFragment = selectedFragment
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }

        fun Fragment.newInstance(): Fragment =
            this.apply {
                arguments = Bundle()
            }
    }
}
