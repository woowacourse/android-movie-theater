package woowacourse.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.utils.MovieUtils.navigateToFragment
import woowacourse.movie.view.ReservationHistoryFragment
import woowacourse.movie.view.SettingFragment
import woowacourse.movie.view.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.main = this
        setBottomNavigationView()
    }

    @SuppressLint("ResourceType")
    private fun setBottomNavigationView() {
        val fragments = mapOf(
            R.id.fragment_home to HomeFragment(),
            R.id.fragment_list to ReservationHistoryFragment(),
            R.id.fragment_setting to SettingFragment()
        )
        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            fragments[item.itemId]?.let { currentFragment ->
                supportFragmentManager.navigateToFragment(
                    R.id.fragment_container_main,
                    currentFragment
                )
                true
            } ?: false
        }
        binding.bottomNavigationMain.selectedItemId = R.id.fragment_home
    }
}
