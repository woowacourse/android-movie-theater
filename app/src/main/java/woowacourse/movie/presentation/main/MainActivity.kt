package woowacourse.movie.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.BaseActivity
import woowacourse.movie.presentation.movie.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment
import woowacourse.movie.presentation.ticket.list.TicketListFragment

class MainActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    MainContract.View {
    private val presenter: MainPresenter by lazy { MainPresenter(this, (application as MovieApplication).settingRepository) }

    private val ticketListFragment = TicketListFragment()
    private val movieListFragment = MovieListFragment()
    private val settingsFragment = SettingsFragment()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            presenter.saveNotificationSetting(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNavigationView()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
        presenter.checkPermissions()
    }

    override fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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
