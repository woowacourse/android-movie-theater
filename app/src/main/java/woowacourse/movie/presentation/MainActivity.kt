package woowacourse.movie.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.movies.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment
import woowacourse.movie.presentation.ticket.TicketListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val ticketListFragment = TicketListFragment()
    private val movieListFragment = MovieListFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_list -> {
                    replaceFragment(ticketListFragment)
                    true
                }

                R.id.action_home -> {
                    replaceFragment(movieListFragment)
                    true
                }

                R.id.action_settings -> {
                    replaceFragment(settingsFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.mainContainer.id, fragment)
        }
    }
}
