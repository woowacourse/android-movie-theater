package woowacourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.reservationlist.ReservationListFragment
import woowacourse.movie.reservationlist.SettingFragment
import woowacourse.movie.screeningmovie.ScreeningMovieFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nav = findViewById<BottomNavigationView>(R.id.bnv_main)
        val container = findViewById<FragmentContainerView>(R.id.fcv_main)
        val screeningMovieFragment = ScreeningMovieFragment()
        val settingMovieFragment = SettingFragment()
        val reservationListFragment = ReservationListFragment()
        nav.selectedItemId = R.id.menu_home
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: changeFragment(
            screeningMovieFragment
        )
        nav.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home ->
                        screeningMovieFragment

                    R.id.menu_setting ->
                        settingMovieFragment

                    R.id.menu_reservation_list ->
                        reservationListFragment

                    else -> screeningMovieFragment
                }
            )
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main, fragment)
            addToBackStack(null)
        }
    }
}

