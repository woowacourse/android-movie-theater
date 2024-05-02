package woowacourse.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservationhistory.ReservationHistoryFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, ReservationHistoryFragment())
        }
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_history -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view, ReservationHistoryFragment())
                    }
                }

                R.id.screen_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view, HomeFragment())
                    }
                }

                R.id.setting -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view, SettingFragment())
                    }
                }
            }
            true
        }
    }
}
