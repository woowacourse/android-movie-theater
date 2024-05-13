package woowacourse.movie.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.presentation.homefragments.movieList.MovieListFragment
import woowacourse.movie.presentation.homefragments.reservationList.ReservationListFragment
import woowacourse.movie.presentation.homefragments.setting.SettingFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val movieListFragment by lazy { MovieListFragment() }
    private val reservationListFragment by lazy { ReservationListFragment() }
    private val settingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestNotificationPermission()

        if (savedInstanceState == null) {
            setupBottomNavigationView()
            selectDefaultMenuItem()
        }
    }

    private fun setupBottomNavigationView() {
        setupInitialFragment()
        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            val selectedMenu = matchedFragment(menu)
            replaceFragment(selectedMenu)
            true
        }
    }

    private fun setupInitialFragment() {
        supportFragmentManager.commit {
            add(R.id.fragment_container, movieListFragment)
        }
    }

    private fun selectDefaultMenuItem() {
        binding.bottomNavigationView.selectedItemId = R.id.action_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, TAG_REPLACE_FRAGMENT)
        }
    }

    private fun matchedFragment(menu: MenuItem): Fragment {
        return when (menu.itemId) {
            R.id.action_home -> movieListFragment
            R.id.action_reservation_list -> reservationListFragment
            R.id.action_settings -> settingFragment
            else -> movieListFragment
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(this, "알림 권한 요청을 거부하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { _: Boolean -> }

    companion object {
        private const val TAG_REPLACE_FRAGMENT = "changedFragment"
    }
}
