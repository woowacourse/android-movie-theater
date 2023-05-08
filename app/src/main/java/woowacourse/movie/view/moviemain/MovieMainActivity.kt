package woowacourse.movie.view.moviemain

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.AlarmPreference
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.util.requestRequiredPermissions
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding
    private lateinit var presenter: MovieMainContract.Presenter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            presenter.setAlarmPreference(true)
        } else {
            presenter.setAlarmPreference(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MovieMainPresenter(
            AlarmPreference.getInstance(applicationContext)
        )
        setUpBottomNavigation()
        requestNotificationPermission()
    }

    private fun setUpBottomNavigation() {
        binding.navigationView.setOnItemSelectedListener { item ->
            setMenuItemClickListener(item)
        }
        binding.navigationView.selectedItemId = R.id.action_home
    }

    private fun requestNotificationPermission() {
        val permissionsRequired = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissionsRequired.add(Manifest.permission.POST_NOTIFICATIONS)
        this.requestRequiredPermissions(permissionsRequired, requestPermissionLauncher::launch)
    }

    private fun setMenuItemClickListener(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_reservation_list -> {
            replaceFragment<ReservationListFragment>()
            true
        }
        R.id.action_home -> {
            replaceFragment<MovieListFragment>()
            true
        }
        R.id.action_setting -> {
            replaceFragment<SettingFragment>()
            true
        }
        else -> false
    }

    private inline fun <reified T : Fragment> AppCompatActivity.replaceFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.fragment_container_view)
        }
    }
}
