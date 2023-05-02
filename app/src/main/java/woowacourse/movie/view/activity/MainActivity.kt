package woowacourse.movie.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.data.ReservationsViewData
import woowacourse.movie.mapper.ReservationMapper.toView
import woowacourse.movie.system.BroadcastAlarm.createNotificationChannel
import woowacourse.movie.system.ReservationAlarmReceiver
import woowacourse.movie.view.fragment.MovieListFragment
import woowacourse.movie.view.fragment.ReservationListFragment
import woowacourse.movie.view.fragment.SettingFragment
import woowacourse.movie.view.repository.MainRepository

class MainActivity : AppCompatActivity() {
    private val mainRepository: MainRepository = MainRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(
            ReservationAlarmReceiver.RESERVATION_NOTIFICATION_CHANNEL_ID,
            getString(R.string.notification_channel_name),
            getString(R.string.notification_channel_description)
        )

        makeBottomNavigationView()
        replaceFragment<MovieListFragment>()
    }

    private fun makeBottomNavigationView() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.action_home
        bottomNavigationView.setOnItemSelectedListener(::onItemSelected)
    }

    private fun onItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_list -> replaceReservationListFragment()
            R.id.action_home -> replaceFragment<MovieListFragment>()
            R.id.action_setting -> replaceFragment<SettingFragment>()
            else -> return false
        }
        return true
    }

    private fun replaceReservationListFragment() {
        val reservations =
            ReservationsViewData(mainRepository.requestReservation().map { it.toView() })
        val bundle =
            ReservationListFragment.from(reservations)
        replaceFragment<ReservationListFragment>(bundle)
    }

    private inline fun <reified T : Fragment> replaceFragment(bundle: Bundle? = null) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.main_fragment_container_view, "", bundle)
        }
    }
}
