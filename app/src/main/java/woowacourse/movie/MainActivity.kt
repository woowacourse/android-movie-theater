package woowacourse.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.bookinglist.BookingListFragment
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment
import woowacourse.movie.ui.DataBindingBaseActivity

class MainActivity : DataBindingBaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)
    private val bookingListFragment: BookingListFragment by lazy { BookingListFragment() }
    private val moviesFragment: MoviesFragment by lazy { MoviesFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen(binding.root)
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_list -> showFragment(
                        bookingListFragment,
                        BookingListFragment::class.java.name
                    )

                    R.id.action_home -> showFragment(
                        moviesFragment,
                        MoviesFragment::class.java.name
                    )

                    R.id.action_settings -> showFragment(
                        settingFragment,
                        SettingFragment::class.java.name
                    )
                }
                true
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        supportFragmentManager.fragments.forEach {
            fragmentTransaction.hide(it)
        }

        findFragment?.let {
            fragmentTransaction
                .show(it)
                .commit()
        } ?: run {
            fragmentTransaction
                .add(binding.mainContainer.id, fragment, tag)
                .commitAllowingStateLoss()
        }
    }
}
