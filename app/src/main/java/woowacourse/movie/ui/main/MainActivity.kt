package woowacourse.movie.ui.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.fragment.movieList.MovieListFragment
import woowacourse.movie.ui.fragment.reservationList.ReservationListFragment
import woowacourse.movie.ui.fragment.setting.SettingFragment
import woowacourse.movie.util.requestPermissions

class MainActivity : AppCompatActivity() {
    private val fragmentContainer: FragmentContainerView by lazy { findViewById(R.id.container) }
    private val bottomNavigation: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation_view)
    }

    private lateinit var movieListFragment: MovieListFragment
    private lateinit var reservationListFragment: ReservationListFragment
    private lateinit var settingFragment: SettingFragment

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListFragment = getFragment(TAG_MOVIE_LIST, MovieListFragment())
        reservationListFragment = getFragment(TAG_RESERVATION_LIST, ReservationListFragment())
        settingFragment = getFragment(TAG_SETTING, SettingFragment())

        if (savedInstanceState == null) { initFragments() }
        initListener()
        requestPermissions(PERMISSIONS, requestPermissionLauncher::launch)
    }

    private inline fun <reified T : Fragment> getFragment(tag: String, default: T): T {
        return supportFragmentManager.findFragmentByTag(tag) as? T ?: default
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
            .add(fragmentContainer.id, movieListFragment, TAG_MOVIE_LIST)
            .add(fragmentContainer.id, reservationListFragment, TAG_RESERVATION_LIST)
            .add(fragmentContainer.id, settingFragment, TAG_SETTING)
            .hide(reservationListFragment)
            .hide(settingFragment)
            .commit()
        bottomNavigation.selectedItemId = R.id.movie_list_item
    }

    private fun initListener() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_list_item -> {
                    supportFragmentManager.beginTransaction()
                        .show(reservationListFragment)
                        .hide(movieListFragment)
                        .hide(settingFragment)
                        .commit()
                }
                R.id.movie_list_item -> {
                    supportFragmentManager.beginTransaction()
                        .hide(reservationListFragment)
                        .show(movieListFragment)
                        .hide(settingFragment)
                        .commit()
                }
                R.id.setting_item -> {
                    supportFragmentManager.beginTransaction()
                        .hide(reservationListFragment)
                        .hide(movieListFragment)
                        .show(settingFragment)
                        .commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    companion object {
        private const val TAG_RESERVATION_LIST = "tag_reservation_list"
        private const val TAG_MOVIE_LIST = "tag_movie_list"
        private const val TAG_SETTING = "tag_setting"

        val PERMISSIONS = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }
}
