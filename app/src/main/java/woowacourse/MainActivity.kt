package woowacourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.reservationlist.ReservationListFragment
import woowacourse.movie.reservationlist.SettingFragment
import woowacourse.movie.screeningmovie.ScreeningMovieFragment

class MainActivity : AppCompatActivity() {
    private lateinit var screeningMovieFragment: ScreeningMovieFragment
    private lateinit var settingMovieFragment: SettingFragment
    private lateinit var reservationListFragment: ReservationListFragment
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screeningMovieFragment = ScreeningMovieFragment()
        settingMovieFragment = SettingFragment()
        reservationListFragment = ReservationListFragment()

        binding.bnvMain.selectedItemId = R.id.menu_home
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: changeFragment(
            screeningMovieFragment
        )
        setNavigation()
    }

    private fun setNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
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

