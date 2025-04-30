package woowacourse.movie.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.cinema.HomeFragment
import woowacourse.movie.view.reservation.ReservationHistoryFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main,
            )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view_main, HomeFragment())
            }
        }
        binding.main = this
        binding.bottomNavigationViewMain.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_menu_main_reservation_history -> {
                    replaceWith(ReservationHistoryFragment())
                }

                R.id.item_menu_main_home -> {
                    replaceWith(HomeFragment())
                }

                R.id.item_menu_main_setting -> {
                    replaceWith(SettingFragment())
                }

                else -> false
            }
        }
    }

    private fun replaceWith(fragment: Fragment): Boolean {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view_main, fragment)
        }
        return true
    }
}
