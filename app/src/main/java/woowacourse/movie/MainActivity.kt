package woowacourse.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.common.DataBindingBaseActivity
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.bookinglist.BookingListFragment
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.presentation.settings.SettingsFragment

class MainActivity : DataBindingBaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    override lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen()
        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_list -> {
                    replaceFragment(BookingListFragment())
                    true
                }

                R.id.action_home -> {
                    replaceFragment(MoviesFragment())
                    true
                }

                R.id.action_settings -> {
                    replaceFragment(SettingsFragment())
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
