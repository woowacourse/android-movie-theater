package woowacourse.movie.presentation.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.presentation.homefragments.movieList.MovieListFragment
import woowacourse.movie.presentation.homefragments.reservation.ReservationFragment
import woowacourse.movie.presentation.homefragments.setting.SettingFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val movieListFragment by lazy { MovieListFragment() }
    private val reservationFragment by lazy { ReservationFragment() }
    private val settingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            setupBottomNavigationView()
            selectDefaultMenuItem()
        }
    }

    private fun setupBottomNavigationView() {
        setupInitialFragment()
        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            val selectedMenu = matchedFragment(menu)
            replaceFragment(selectedMenu)
            true
        }
    }

    private fun setupInitialFragment() {
        supportFragmentManager.commit {
            add(R.id.fragment_container, movieListFragment)
        }
    }

    private fun selectDefaultMenuItem() {
        binding.bottomNavigationView.selectedItemId = R.id.action_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment)
        }
    }

    private fun matchedFragment(menu: MenuItem): Fragment {
        return when (menu.itemId) {
            R.id.action_home -> movieListFragment
            R.id.action_reservation_list -> reservationFragment
            R.id.action_settings -> settingFragment
            else -> movieListFragment
        }
    }
}
