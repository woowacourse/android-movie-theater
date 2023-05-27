package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.fragment.movielist.MovieListFragment
import woowacourse.movie.fragment.reservationlist.ReservationListFragment
import woowacourse.movie.fragment.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        makeBottomNavigationView()
    }

    private fun makeBottomNavigationView() {
        binding.mainBottomNavigationView.selectedItemId = R.id.action_home
        binding.mainBottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelect)
    }

    private fun onNavigationItemSelect(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                replaceFragment<ReservationListFragment>()
                true
            }
            R.id.action_home -> {
                replaceFragment<MovieListFragment>()
                true
            }
            R.id.action_setting -> {
                replaceFragment<SettingFragment>()
                true
            }
            else -> false
        }
    }

    private inline fun <reified T : Fragment> replaceFragment(bundle: Bundle? = null) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.main_fragment_container_view, "", bundle)
        }
    }
}
