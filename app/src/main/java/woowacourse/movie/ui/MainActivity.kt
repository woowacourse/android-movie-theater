package woowacourse.movie.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservation.ReservationFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.main_bottom_navigation_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragmentContainerView()
        setBottomNavigationView()
    }

    private fun initFragmentContainerView() {
        supportFragmentManager.commit {
            add(R.id.main_fragment_view, ReservationFragment())
            setReorderingAllowed(true)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.main_fragment_view, fragment)
            setReorderingAllowed(true)
        }
    }

    private fun setBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { selectedIcon ->
            changeFragment(
                getFragmentByIcon(selectedIcon)
            )
            true
        }
    }

    private fun getFragmentByIcon(item: MenuItem): Fragment = when (item.itemId) {
        R.id.menu_item_reservation -> ReservationFragment()
        R.id.menu_item_home -> HomeFragment()
        R.id.menu_item_setting -> SettingFragment()
        else -> throw IllegalArgumentException()
    }
}
