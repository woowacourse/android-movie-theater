package woowacourse.movie.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.feature.BookingHistoryFragment
import woowacourse.movie.feature.SettingFragment
import woowacourse.movie.feature.home.view.HomeFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fcv_main, HomeFragment())
            }
        }

        binding.bottomNavMain.selectedItemId = R.id.item_home
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_booking_history -> {
                    replaceFragment(BookingHistoryFragment())
                    true
                }

                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.item_setting -> {
                    replaceFragment(SettingFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }
}
