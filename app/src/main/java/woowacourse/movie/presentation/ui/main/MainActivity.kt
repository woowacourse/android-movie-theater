package woowacourse.movie.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryFragment
import woowacourse.movie.presentation.ui.main.home.HomeFragment
import woowacourse.movie.presentation.ui.main.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            binding?.bottomNavigation?.selectedItemId = R.id.fragment_home
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fc_main, HomeFragment())
            }
        }
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding?.bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fc_main, HomeFragment()).commit()
                    true
                }

                R.id.fragment_setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fc_main, SettingFragment()).commit()
                    true
                }

                R.id.fragment_reservation_history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fc_main, ReservationHistoryFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
