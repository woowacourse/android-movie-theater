package woowacourse.movie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.fragment.movieList.MovieListFragment
import woowacourse.movie.ui.fragment.reservationList.ReservationListFragment

class MainActivity : AppCompatActivity() {
    private val rv: FragmentContainerView by lazy { findViewById(R.id.container) }
    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation_view) }

    private lateinit var movieListFragment: MovieListFragment
    private lateinit var reservationListFragment: ReservationListFragment
    private lateinit var settingFragment: ReservationListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(rv.id)

        when (fragment) {
            is MovieListFragment -> {
                movieListFragment = fragment
                reservationListFragment = ReservationListFragment()
                bottomNavigation.selectedItemId = R.id.movie_list_item
            }
            is ReservationListFragment -> {
                movieListFragment = MovieListFragment()
                reservationListFragment = fragment
                bottomNavigation.selectedItemId = R.id.reservation_list_item
            }
            null -> {
                movieListFragment = MovieListFragment()
                reservationListFragment = ReservationListFragment()
                supportFragmentManager.beginTransaction().add(
                    rv.id, movieListFragment,
                    MOVIE_LIST_TAG
                ).commit()
                bottomNavigation.selectedItemId = R.id.movie_list_item
            }
        }

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_list_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(rv.id, reservationListFragment, RESERVATION_LIST_TAG)
                        .commit()
                }
                R.id.movie_list_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(rv.id, movieListFragment, MOVIE_LIST_TAG)
                        .commit()
                }
                R.id.setting_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(rv.id, settingFragment, SETTING_TAG)
                        .commit()
                }
            }
            return@setOnItemSelectedListener true
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
