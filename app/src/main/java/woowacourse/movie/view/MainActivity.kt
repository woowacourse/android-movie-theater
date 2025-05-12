package woowacourse.movie.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.alarm.AlarmReceiver
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.base.BaseActivity
import woowacourse.movie.view.movies.MovieListFragment
import woowacourse.movie.view.reservation.history.ReservationHistoryFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        if (savedInstanceState == null) {
            navigateToFragment(MovieListFragment())
            binding.bottomNavigationView.selectedItemId = R.id.menu_home
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            return@setOnItemSelectedListener when (menu.itemId) {
                R.id.menu_list -> {
                    navigateToFragment(ReservationHistoryFragment())
                    true
                }

                R.id.menu_home -> {
                    navigateToFragment(MovieListFragment())
                    true
                }

                R.id.menu_settings -> {
                    navigateToFragment(SettingsFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_container_view, fragment)
        }
    }

    fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                AlarmReceiver.CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = getString(R.string.notification_channel_description)
            }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
