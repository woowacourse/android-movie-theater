package woowacourse.movie.activity

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.fragment.MoviesFragment
import woowacourse.movie.fragment.ReservationListFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(ReservationListFragment())
        setOnBottomNavigationClickListener()
    }

    private fun setOnBottomNavigationClickListener() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bottom_navigation -> replaceFragment(MoviesFragment())
                R.id.setting_bottom_navigation -> replaceFragment(SettingFragment())
                R.id.reservation_list_bottom_navigation -> replaceFragment(ReservationListFragment())
            }
            true
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.main_fragment, fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment, fragment)
        }
    }
}
