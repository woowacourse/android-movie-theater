package woowacourse.movie.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.bookinghistory.BookingHistoryFragment
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment
import woowacourse.movie.presentation.DataBindingBaseActivity

class MainActivity : DataBindingBaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen(binding.root)
        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_list -> showFragment(BookingHistoryFragment::class.java)
                    R.id.action_home -> showFragment(MoviesFragment::class.java)
                    R.id.action_settings -> showFragment(SettingFragment::class.java)
                }
                true
            }
        }
    }

    private fun showFragment(clazz: Class<out Fragment>) {
        val findFragment = supportFragmentManager.findFragmentByTag(clazz.name)
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
                .add(R.id.main_container, clazz, null, clazz.name)
                .commitAllowingStateLoss()
        }
    }
}
