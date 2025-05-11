package woowacourse.movie.view

import android.os.Bundle
import android.view.MenuItem
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
        setMenuBarEventListener()
        setMenuBarReselectedListener()
        savedInstanceState ?: run {
            binding.bottomNavView.setSelectedItemId(R.id.home)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setMenuBarEventListener() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            supportFragmentManager.commit {
                replace(R.id.fragment_container_main, menuFragment(item))
            }
            true
        }
    }

    private fun setMenuBarReselectedListener() {
        binding.bottomNavView.setOnItemReselectedListener {
            true
        }
    }

    private fun menuFragment(item: MenuItem): Fragment {
        return when (item.itemId) {
            R.id.reservation_list -> ReservationListFragment()
            R.id.home -> MoviesFragment()
            R.id.settings -> SettingFragment()
            else -> MoviesFragment()
        }
    }
}
