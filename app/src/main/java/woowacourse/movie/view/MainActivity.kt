package woowacourse.movie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.movies.MoviesFragment
import woowacourse.movie.view.reservelist.ReservationListFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var selectedFragment: Fragment

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.reservation_list -> selectedFragment = ReservationListFragment()
                R.id.home -> selectedFragment = MoviesFragment()
                R.id.settings -> selectedFragment = SettingFragment()
            }

            supportFragmentManager.commit {
                replace(R.id.fragment_container_main, selectedFragment)
            }
            true
        }
        binding.bottomNavView.setSelectedItemId(R.id.home)
    }
}
