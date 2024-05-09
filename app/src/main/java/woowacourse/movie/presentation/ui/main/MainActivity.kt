package woowacourse.movie.presentation.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.data.repository.local.PreferenceRepositoryImpl
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.base.BaseMvpBindingActivity
import woowacourse.movie.presentation.permision.Permission
import woowacourse.movie.presentation.permision.Permission.toPermissionText
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryFragment
import woowacourse.movie.presentation.ui.main.home.HomeFragment
import woowacourse.movie.presentation.ui.main.setting.SettingFragment

class MainActivity : BaseMvpBindingActivity<ActivityMainBinding>(), MainContract.View {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val presenter: MainContract.Presenter by lazy {
        MainPresenter(this, PreferenceRepositoryImpl())
    }

    override fun initStartView() {
        binding.bottomNavigationViewMain.selectedItemId = R.id.fragment_home
        replaceFragment(HomeFragment(), HomeFragment.TAG)
        setupBottomNavigationView()
        if (Permission.notificationPermissions.isNotEmpty()) {
            requestNotificationPermissions(Permission.notificationPermissions.toList())
        }
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

    private fun requestNotificationPermissions(permissionList: List<String>) {
        val requestList = ArrayList<String>()
        for (permission in permissionList) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestList.add(permission)
            }

            if (requestList.isNotEmpty()) {
                requestMultiplePermissionsLauncher.launch(requestList.toTypedArray())
                return
            }
        }
    }

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                presenter.changeNotificationMode(true)
            } else {
                val stringResId =
                    permissions.entries.find { !it.value }?.key?.toPermissionText()
                        ?: R.string.permission_default_text

                showPermissionSnackBar(stringResId)
            }
        }

    private fun showPermissionSnackBar(
        @StringRes stringResId: Int,
    ) {
        presenter.requestNotificationPermission(getString(stringResId)) {
            setAction(getString(R.string.permission_done)) {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                startActivity(intent)
            }
        }
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
