package woowacourse.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservation.ReservationFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.main_fragment_view, ReservationFragment()).commit()

        findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view).setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_item_reservation -> ReservationFragment()
                    R.id.menu_item_home -> HomeFragment()
                    R.id.menu_item_setting -> SettingFragment()
                    else -> throw IllegalArgumentException()
                }
            )
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, fragment).commit()
    }
}
