package woowacourse.movie.presentation.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R

class NavigationActivity : AppCompatActivity() {
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.navigationView)
    }
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val reservationListFragment: ReservationListFragment by lazy { ReservationListFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setBottomNavigationView()
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.home_fragment
        }
    }

    private fun setBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> {
                    replaceFragment(homeFragment)
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
}
