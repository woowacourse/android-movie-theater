package woowacourse.movie.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.main.movieslist.MoviesFragment
import woowacourse.movie.view.main.reservationlist.ReservationListFragment
import woowacourse.movie.view.main.setting.SettingFragment

class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter: MainContract.Presenter by lazy { MainPresenter(this) }
    private lateinit var binding: ActivityMainBinding
    private val moviesFragment = MoviesFragment()
    private val settingFragment = SettingFragment()
    private val reservationListFragment = ReservationListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.bottomNavigation.setOnItemSelectedListener {
            val screen = when (it.itemId) {
                R.id.home_bottom_navigation -> Screen.Movies
                R.id.setting_bottom_navigation -> Screen.Setting
                R.id.reservation_list_bottom_navigation -> Screen.ReservationList
                else -> throw IllegalArgumentException()
            }
            presenter.changeScreen(screen)
            true
        }
        binding.bottomNavigation.selectedItemId = getSavedNavigationItemId(savedInstanceState)
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (!isPermissionGranted()) {
            showNotificationPermissionDialog()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showNotificationPermissionDialog() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            showWarningToastMessage()
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun showWarningToastMessage() {
        Toast.makeText(
            this,
            getString(R.string.notification_refuse_two_time_warning_message),
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment, fragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_ITEM_ID, binding.bottomNavigation.selectedItemId)
    }

    private fun getSavedNavigationItemId(savedInstanceState: Bundle?): Int {
        return savedInstanceState?.getInt(KEY_SELECTED_ITEM_ID) ?: R.id.home_bottom_navigation
    }

    override fun setScreen(screen: Screen) {
        when (screen) {
            Screen.Movies -> {
                replaceFragment(moviesFragment)
            }
            Screen.Setting -> {
                replaceFragment(settingFragment)
            }
            Screen.ReservationList -> {
                replaceFragment(reservationListFragment)
            }
        }
    }

    companion object {
        private const val KEY_SELECTED_ITEM_ID = "KEY_SELECTED_ITEM_ID"
    }
}
