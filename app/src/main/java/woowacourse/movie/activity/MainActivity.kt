package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.fragment.movielist.MovieListFragment
import woowacourse.movie.fragment.reservationlist.ReservationListFragment
import woowacourse.movie.fragment.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeBottomNavigationView()
    }

    private fun makeBottomNavigationView() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.action_home
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelect)
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
