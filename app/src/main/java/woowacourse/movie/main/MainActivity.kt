package woowacourse.movie.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.fragment.movieList.MovieListFragment
import woowacourse.movie.ui.fragment.reservationList.ReservationListFragment
import woowacourse.movie.ui.fragment.setting.SettingFragment
import woowacourse.movie.util.requestPermissions

class MainActivity : AppCompatActivity() {
    private val rv: FragmentContainerView by lazy { findViewById(R.id.container) }
    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation_view) }

    private lateinit var movieListFragment: MovieListFragment
    private lateinit var reservationListFragment: ReservationListFragment
    private lateinit var settingFragment: SettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListFragment =
            supportFragmentManager.findFragmentByTag(MOVIE_LIST_TAG) as? MovieListFragment
                ?: MovieListFragment()

        reservationListFragment =
            supportFragmentManager.findFragmentByTag(RESERVATION_LIST_TAG) as? ReservationListFragment
                ?: ReservationListFragment()

        settingFragment =
            supportFragmentManager.findFragmentByTag(SETTING_TAG) as? SettingFragment
                ?: SettingFragment()

        if (savedInstanceState == null) { initFragments() }
        initListener()
        requestPermissions(PERMISSIONS, requestPermissionLauncher::launch)
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
            .add(rv.id, movieListFragment, MOVIE_LIST_TAG)
            .add(rv.id, reservationListFragment, RESERVATION_LIST_TAG)
            .add(rv.id, settingFragment, SETTING_TAG)
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
    }

    companion object {
        internal const val KEY_MOVIE = "key_movie"
        internal const val KEY_ADV = "key_adb"

        private const val RESERVATION_LIST_TAG = "reservation_list_tag"
        private const val MOVIE_LIST_TAG = "movie_list_tag"
        private const val SETTING_TAG = "setting_tag"

        val PERMISSIONS = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }
}
