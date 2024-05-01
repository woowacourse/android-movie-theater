package woowacourse.movie.presentation.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.homefragments.movieList.MovieListFragment
import woowacourse.movie.presentation.homefragments.reservation.ReservationFragment
import woowacourse.movie.presentation.homefragments.setting.SettingFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            setupBottomNavigationView()
            selectDefaultMenuItem()
        }
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.bottom_navigation_view)
        setupInitialFragment()
        bottomNavigationView.setOnItemSelectedListener { menu ->
            val selectedMenu = matchedFragment(menu)
            replaceFragment(selectedMenu)
            true
        }
    }

    private fun setupInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MovieListFragment())
            .commit()
    }

    private fun selectDefaultMenuItem() {
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.action_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    private fun matchedFragment(menu: MenuItem): Fragment {
        return when (menu.itemId) {
            R.id.action_home -> MovieListFragment()
            R.id.action_reservation_list -> ReservationFragment()
            R.id.action_settings -> SettingFragment()
            else -> MovieListFragment()
        }
    }
}
