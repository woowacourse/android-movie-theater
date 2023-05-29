package woowacourse.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservation.ReservationFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val fragments = mapOf(
        FRAGMENT_HOME to HomeFragment(),
        FRAGMENT_RESERVATION to ReservationFragment(),
        FRAGMENT_SETTING to SettingFragment(),
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragmentContainerView()
        setBottomNavigationView()
    }

    private fun initFragmentContainerView() {
        supportFragmentManager.commit {
            add(R.id.main_fragment_view, HomeFragment(), FRAGMENT_HOME)
            setReorderingAllowed(true)
        }
    }

    private fun setBottomNavigationView() {
        binding.mainBottomNavigationView.selectedItemId = R.id.menu_item_home
        binding.mainBottomNavigationView.setOnItemSelectedListener { selectedIcon ->
            changeFragment(getTag(selectedIcon.itemId))
            true
        }
    }

    private fun changeFragment(tag: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
        }

        hideShowingFragment()

        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.commit {
            if (findFragment != null) {
                show(findFragment)
            } else {
                val fragment: Fragment = fragments[tag] ?: throw IllegalArgumentException()
                add(R.id.main_fragment_view, fragment, tag)
            }
        }
    }

    private fun hideShowingFragment() {
        val currentTag = getTag(binding.mainBottomNavigationView.selectedItemId)
        supportFragmentManager.commit {
            supportFragmentManager.findFragmentByTag(currentTag)?.let { hide(it) }
        }
    }

    private fun getTag(itemId: Int): String = when (itemId) {
        R.id.menu_item_home -> FRAGMENT_HOME
        R.id.menu_item_reservation -> FRAGMENT_RESERVATION
        R.id.menu_item_setting -> FRAGMENT_SETTING
        else -> throw IllegalArgumentException()
    }

    companion object {
        private const val FRAGMENT_HOME = "home"
        private const val FRAGMENT_RESERVATION = "reservation"
        private const val FRAGMENT_SETTING = "setting"
    }
}
