package woowacourse.movie.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
    private val fragmentContainerView: FragmentContainerView by lazy { findViewById(R.id.container) }
    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation_view) }

    private val movieListFragment: MovieListFragment by lazy {
        supportFragmentManager.findFragmentByTag(MOVIE_LIST_TAG) as? MovieListFragment
            ?: MovieListFragment()
    }
    private val reservationListFragment: ReservationListFragment by lazy {
        supportFragmentManager.findFragmentByTag(RESERVATION_LIST_TAG) as? ReservationListFragment
            ?: ReservationListFragment()
    }
    private val settingFragment: SettingFragment by lazy {
        supportFragmentManager.findFragmentByTag(SETTING_TAG) as? SettingFragment
            ?: SettingFragment()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) { initFragments() }
        initListener()
        requestPermissions(PERMISSIONS, requestPermissionLauncher::launch)
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerView.id, movieListFragment)
            .commit()
        bottomNavigation.selectedItemId = R.id.movie_list_item
    }

    private fun initListener() {
        bottomNavigation.setOnItemSelectedListener {
            bottomNavigationItemClickEvent(it)
            return@setOnItemSelectedListener true
        }
    }

    private fun bottomNavigationItemClickEvent(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.reservation_list_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(fragmentContainerView.id, reservationListFragment)
                    .commit()
            }
            R.id.movie_list_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(fragmentContainerView.id, movieListFragment)
                    .commit()
            }
            R.id.setting_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(fragmentContainerView.id, settingFragment)
                    .commit()
            }
        }
    }

    companion object {
        internal const val KEY_MOVIE = "key_movie"
        internal const val KEY_ADV = "key_adb"

        private const val RESERVATION_LIST_TAG = "reservation_list_tag"
        private const val MOVIE_LIST_TAG = "movie_list_tag"
        private const val SETTING_TAG = "setting_tag"

        val PERMISSIONS = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    }
}
