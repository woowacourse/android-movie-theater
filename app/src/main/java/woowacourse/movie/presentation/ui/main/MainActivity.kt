package woowacourse.movie.presentation.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.data.repository.PreferenceRepositoryImpl
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.base.BaseMvpBindingActivity
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryFragment
import woowacourse.movie.presentation.ui.main.home.HomeFragment
import woowacourse.movie.presentation.ui.main.setting.SettingFragment

class MainActivity() : BaseMvpBindingActivity<ActivityMainBinding>(), MainContract.View {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val presenter: MainContract.Presenter by lazy {
        MainPresenter(this, PreferenceRepositoryImpl())
    }

    override fun initStartView() {
        binding.bottomNavigationViewMain.selectedItemId = R.id.fragment_home
        replaceFragment(HomeFragment(), HomeFragment.TAG)
        setupBottomNavigationView()
        requestNotificationPermission()
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

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            presenter.changeNotificationMode(isGranted)
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
