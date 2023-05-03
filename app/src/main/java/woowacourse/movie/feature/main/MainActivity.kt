package woowacourse.movie.feature.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.feature.movielist.MovieListFragment
import woowacourse.movie.feature.reservation.list.ReservationListFragment
import woowacourse.movie.feature.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val fragmentContainerView: FragmentContainerView by lazy { findViewById(R.id.container) }
    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation_view) }

    private val movieListFragment: MovieListFragment by lazy {
        getFragment(MOVIE_LIST_TAG, MovieListFragment())
    }
    private val reservationListFragment: ReservationListFragment by lazy {
        getFragment(RESERVATION_LIST_TAG, ReservationListFragment())
    }
    private val settingFragment: SettingFragment by lazy {
        getFragment(SETTING_TAG, SettingFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) { initFragments() }
        initListener()
    }

    private inline fun <reified T : Fragment> getFragment(tag: String, default: T): T {
        return supportFragmentManager.findFragmentByTag(tag) as? T ?: default
    }

    private fun initFragments() {
        supportFragmentManager.commit {
            replace(fragmentContainerView.id, movieListFragment)
        }
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
                supportFragmentManager.commit {
                    replace(fragmentContainerView.id, reservationListFragment)
                }
            }
            R.id.movie_list_item -> {
                supportFragmentManager.commit {
                    replace(fragmentContainerView.id, movieListFragment)
                }
            }
            R.id.setting_item -> {
                supportFragmentManager.commit {
                    replace(fragmentContainerView.id, settingFragment)
                }
            }
        }
    }

    companion object {
        internal const val KEY_MOVIE = "key_movie"
        internal const val KEY_ADV = "key_adb"

        private const val RESERVATION_LIST_TAG = "reservation_list_tag"
        private const val MOVIE_LIST_TAG = "movie_list_tag"
        private const val SETTING_TAG = "setting_tag"
    }
}
