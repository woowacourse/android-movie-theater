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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fcv_main, HomeFragment())
            }
        }

        binding.bottomNavMain.selectedItemId = item_home
        setupNavigationItemClickListener()
    }

    private fun setupNavigationItemClickListener() {
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                item_booking_history -> replaceFragment(BookingHistoryFragment())

                item_home -> replaceFragment(HomeFragment())

                item_setting -> replaceFragment(SettingFragment())

                else -> false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fcv_main, fragment)
            .commit()
    }
}
