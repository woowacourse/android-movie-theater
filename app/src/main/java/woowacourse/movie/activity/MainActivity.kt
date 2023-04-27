package woowacourse.movie.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.domain.AdvertisementMock
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy
import woowacourse.movie.fragment.ReservationListFragment
import woowacourse.movie.fragment.SettingFragment
import woowacourse.movie.system.PermissionLauncher
import woowacourse.movie.system.PermissionLauncherProvider
import woowacourse.movie.view.ReservationAlarmReceiver
import woowacourse.movie.view.adapter.MovieAdapter
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.ReservationsViewData
import woowacourse.movie.view.mapper.MovieMapper.toDomain
import woowacourse.movie.view.repository.MainRepository

class MainActivity : AppCompatActivity() {
    private val mainRepository: MainRepository = MainRepository()
    val permissionLauncher: PermissionLauncher = PermissionLauncher(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionLauncherProvider.permissionLaunchers[this] = permissionLauncher
        createNotificationChannel(applicationContext)

        makeMovieRecyclerView()
        makeBottomNavigationView()
    }

    private fun createNotificationChannel(context: Context?) {
        val channel = NotificationChannel(
            ReservationAlarmReceiver.RESERVATION_NOTIFICATION_CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notification_channel_description)
        }

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun makeMovieRecyclerView() {
        val movies = mainRepository.requestMovie().map { it.toDomain() }
        val advertisementDatas = AdvertisementMock.createAdvertisements()
        val advertisementPolicy = MovieAdvertisementPolicy(MOVIE_COUNT, ADVERTISEMENT_COUNT)

        val movieRecyclerView = findViewById<RecyclerView>(R.id.main_movie_list)
        movieRecyclerView.adapter = MovieAdapter(
            movies, advertisementDatas, advertisementPolicy, ::onClickItem
        )
    }

    private fun onClickItem(data: MovieListViewData) {
        when (data.viewType) {
            MovieListViewType.MOVIE -> MovieReservationActivity.from(
                this, data as MovieViewData
            ).run {
                startActivity(this)
            }
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }

    private fun makeBottomNavigationView() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.action_home
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_list -> {
                    replaceFragment<ReservationListFragment>(
                        ReservationListFragment.from(
                            ReservationsViewData(mainRepository.requestReservation())
                        )
                    )
                    true
                }
                R.id.action_home -> {
                    removeAllFragments()
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

    private inline fun <reified T : Fragment> replaceFragment(bundle: Bundle? = null) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(R.id.main_fragment_container_view, "", bundle)
        }
    }

    private fun removeAllFragments() {
        supportFragmentManager.run {
            commit {
                fragments.forEach {
                    remove(it)
                }
            }
        }
    }

    companion object {
        private const val MOVIE_COUNT = 3
        private const val ADVERTISEMENT_COUNT = 1
    }
}
