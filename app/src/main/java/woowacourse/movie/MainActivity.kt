package woowacourse.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.common.BaseActivity
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.bookinglist.BookingListFragment
import woowacourse.movie.presentation.movies.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
    }

    private fun setBottomNavigationView() {
        val bookingListFragment = BookingListFragment()
        val movieListFragment = MovieListFragment()
        val settingsFragment = SettingsFragment()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_list -> {
                    replaceFragment(bookingListFragment)
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
