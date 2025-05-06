package woowacourse.movie.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.R.id.fcv_main
import woowacourse.movie.R.id.item_booking_history
import woowacourse.movie.R.id.item_home
import woowacourse.movie.R.id.item_setting
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.feature.bookinghistory.BookingHistoryFragment
import woowacourse.movie.feature.home.view.HomeFragment
import woowacourse.movie.feature.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main,
        )
    }

    private val fragments =
        mapOf(
            item_home to HomeFragment(),
            item_booking_history to BookingHistoryFragment(),
            item_setting to SettingFragment(),
        )

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            activeFragment = fragments[item_home]
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fcv_main, activeFragment!!, item_home.toString())
            }
        } else {
            activeFragment = supportFragmentManager.findFragmentById(fcv_main)
        }

        binding.bottomNavMain.selectedItemId = item_home
        setupNavigationItemClickListener()
    }

    private fun setupNavigationItemClickListener() {
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                item_home,
                item_booking_history,
                item_setting,
                -> switchFragment(item.itemId)

                else -> false
            }
        }
    }

    private fun switchFragment(id: Int): Boolean {
        val newFragment =
            supportFragmentManager.findFragmentByTag(id.toString())
                ?: fragments[id] ?: return false

        if (newFragment == activeFragment) return false

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            activeFragment?.let { hide(it) }

            if (!newFragment.isAdded) {
                add(fcv_main, newFragment, id.toString())
            } else {
                show(newFragment)
            }
        }

        activeFragment = newFragment
        return true
    }
}
