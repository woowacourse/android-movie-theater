package woowacourse.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.feature.history.ReservationHistoryFragment
import woowacourse.movie.feature.home.HomeFragment
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
        binding.main = this
        setBottomNavigationView()
    }

    @SuppressLint("ResourceType")
    private fun setBottomNavigationView() {
        val fragments =
            mapOf(
                R.id.fragment_home to HomeFragment(),
                R.id.fragment_list to ReservationHistoryFragment(),
                R.id.fragment_setting to SettingFragment(),
            )
        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            fragments[item.itemId]?.let { currentFragment ->
                supportFragmentManager.navigateToBottomMenu(
                    R.id.fragment_container_main,
                    currentFragment,
                )
                true
            } ?: false
        }
        binding.bottomNavigationMain.selectedItemId = R.id.fragment_home
    }

    @SuppressLint("ResourceType")
    private fun FragmentManager.navigateToBottomMenu(
        @LayoutRes
        fragmentLayoutResource: Int,
        nextFragment: Fragment,
    ) {
        commit {
            setReorderingAllowed(true)
            replace(fragmentLayoutResource, nextFragment)
        }
    }
}
