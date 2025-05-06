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

    private var activeFragment: Fragment? = fragments[item_home]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            activeFragment?.let {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(fcv_main, it)
                }
            }
        }

        binding.bottomNavMain.selectedItemId = item_home
        setupNavigationItemClickListener()
    }

    private fun setupNavigationItemClickListener() {
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                item_booking_history -> switchFragment(item_booking_history)

                item_home -> switchFragment(item_home)

                item_setting -> switchFragment(item_setting)

                else -> false
            }
            true
        }
    }

    private fun switchFragment(id: Int): Boolean {
        val newFragment = fragments[id] ?: return false

        if (newFragment == activeFragment) return false

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            activeFragment?.let { hide(it) }
            if (!newFragment.isAdded) {
                add(fcv_main, newFragment)
            }
            show(newFragment)
        }

        activeFragment = newFragment
        return true
    }
}
