package woowacourse.movie.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryFragment
import woowacourse.movie.presentation.ui.main.home.HomeFragment
import woowacourse.movie.presentation.ui.main.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.bottomNavigationViewMain.selectedItemId = R.id.fragment_home
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view_main, HomeFragment())
            }
        }
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationViewMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_main, HomeFragment()).commit()
                    true
                }

                R.id.fragment_setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_main, SettingFragment()).commit()
                    true
                }

                R.id.fragment_reservation_history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_main, ReservationHistoryFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}
