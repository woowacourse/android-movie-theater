package woowacourse.movie.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.fragment.MovieListFragment
import woowacourse.movie.fragment.ReservationListFragment
import woowacourse.movie.fragment.SettingFragment
import woowacourse.movie.service.Notification
import woowacourse.movie.view.data.ReservationsViewData
import woowacourse.movie.view.mapper.ReservationMapper.toView
import woowacourse.movie.view.repository.MainRepository

class MainActivity : AppCompatActivity() {
    private val mainRepository: MainRepository = MainRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Notification.createNotificationChannel(applicationContext)

        makeBottomNavigationView()
    }

    private fun makeBottomNavigationView() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.action_home
        addFragment<MovieListFragment>()
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_list -> {
                    replaceFragment<ReservationListFragment>(
                        ReservationListFragment.from(
                            ReservationsViewData(
                                mainRepository.requestReservation()
                                    .map { reservation -> reservation.toView() }
                            )
                        )
                    )
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
        }
    }

    private inline fun <reified T : Fragment> addFragment() {
        supportFragmentManager.commit {
            add<T>(R.id.main_fragment_container_view)
        }
    }

    private inline fun <reified T : Fragment> replaceFragment(bundle: Bundle? = null) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.main_fragment_container_view, "", bundle)
        }
    }
}
