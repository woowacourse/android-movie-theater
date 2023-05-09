package woowacourse.movie.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.main.FragmentType.HOME
import woowacourse.movie.ui.main.FragmentType.RESERVATION
import woowacourse.movie.ui.main.FragmentType.SETTING
import woowacourse.movie.ui.reservation.ReservationFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragmentContainerView()
        setBottomNavigationView()
    }

    private fun initFragmentContainerView() =
        binding.mainBottomNavigationView.selectedItemId == R.id.menu_item_home

    private fun setBottomNavigationView() {
        replaceFragment<ReservationFragment>()
        binding.mainBottomNavigationView.setOnItemSelectedListener(::getFragmentByIcon)
    }

    private fun getFragmentByIcon(item: MenuItem): Boolean {
        when (FragmentType.valueOf(item.itemId)) {
            RESERVATION -> replaceFragment<ReservationFragment>()
            HOME -> replaceFragment<HomeFragment>()
            SETTING -> replaceFragment<SettingFragment>()
        }
        return true
    }

    private inline fun <reified T : Fragment> replaceFragment() {
        supportFragmentManager.commit {
            replace<T>(R.id.main_fragment_view)
            setReorderingAllowed(true)
        }
    }
}
