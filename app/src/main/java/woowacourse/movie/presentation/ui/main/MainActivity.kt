package woowacourse.movie.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryFragment
import woowacourse.movie.presentation.ui.main.home.HomeFragment
import woowacourse.movie.presentation.ui.main.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.bottomNavigationViewMain.selectedItemId = R.id.fragment_home
            replaceFragment(HomeFragment(), HomeFragment.TAG)
        }
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationViewMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> replaceFragment(HomeFragment(), HomeFragment.TAG)
                R.id.fragment_setting -> replaceFragment(SettingFragment(), SettingFragment.TAG)
                R.id.fragment_reservation_history ->
                    replaceFragment(
                        ReservationHistoryFragment(),
                        ReservationHistoryFragment.TAG,
                    )

                else -> false
            }
        }
    }

    private fun replaceFragment(
        fragment: Fragment,
        tag: String,
    ): Boolean {
        val newFragment = supportFragmentManager.findFragmentByTag(tag)
        if (newFragment != null) {
            supportFragmentManager.popBackStack(tag, 0)
        } else {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view_main, fragment, tag)
                addToBackStack(tag)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_view_main)
            when (currentFragment) {
                is HomeFragment ->
                    binding.bottomNavigationViewMain.selectedItemId =
                        R.id.fragment_home

                is SettingFragment ->
                    binding.bottomNavigationViewMain.selectedItemId =
                        R.id.fragment_setting

                is ReservationHistoryFragment ->
                    binding.bottomNavigationViewMain.selectedItemId =
                        R.id.fragment_reservation_history
            }
        } else {
            finishAffinity()
        }
    }
}
