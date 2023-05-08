package woowacourse.movie.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservation.ReservationFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private var lastSelectedFragmentTag = ""

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
        lastSelectedFragmentTag = FRAGMENT_HOME
    }

    private fun changeFragment(tag: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
        }

        val fragment: Fragment = fragments[tag] ?: throw IllegalArgumentException()

        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.commit {
            supportFragmentManager.findFragmentByTag(lastSelectedFragmentTag)?.let { hide(it) }
        }

        if (findFragment != null) {
            supportFragmentManager.commit {
                show(findFragment)
            }
        } else {
            supportFragmentManager.commit {
                add(R.id.main_fragment_view, fragment, tag)
                setReorderingAllowed(true)
            }
        }

        lastSelectedFragmentTag = tag
    }

    private fun setBottomNavigationView() {
        binding.mainBottomNavigationView.selectedItemId = R.id.menu_item_home
        binding.mainBottomNavigationView.setOnItemSelectedListener { selectedIcon ->
            changeFragment(getTag(selectedIcon))
            true
        }
    }

    private fun getTag(item: MenuItem): String = when (item.itemId) {
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
