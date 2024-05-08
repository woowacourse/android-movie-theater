package woowacourse.movie.presentation

import android.os.Bundle
import android.os.HandlerThread
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.presentation.base.BindingActivity
import woowacourse.movie.presentation.common.replaceTo
import woowacourse.movie.presentation.movieList.MovieListFragment
import woowacourse.movie.presentation.reservation.ReservationFragment
import woowacourse.movie.presentation.setting.SettingFragment

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            binding.botNavMain.selectedItemId = R.id.movieListFragment
            replaceTo<MovieListFragment>(R.id.fragment_container_home)
        }
        initClickListener()
    }

    private fun initClickListener() {
        binding.botNavMain.setOnItemSelectedListener {
            onNavigationItemSelected(it.itemId)
        }
    }

    private fun onNavigationItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.reserveFragment -> replaceTo<ReservationFragment>(R.id.fragment_container_home)
            R.id.movieListFragment -> replaceTo<MovieListFragment>(R.id.fragment_container_home)
            R.id.settingFragment -> replaceTo<SettingFragment>(R.id.fragment_container_home)
            else -> error("Invalid itemId: $itemId")
        }
        return true
    }
}
