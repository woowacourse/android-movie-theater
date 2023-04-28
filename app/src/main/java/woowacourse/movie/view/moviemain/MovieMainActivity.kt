package woowacourse.movie.view.moviemain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment

class MovieMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_main)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation_view)

        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation_list -> {
                    val fragment = supportFragmentManager.findFragmentByTag(TAG_RESERVATION_LIST) as? ReservationListFragment ?: ReservationListFragment().also { addFragment(it, TAG_RESERVATION_LIST) }
                    replaceFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    val fragment = supportFragmentManager.findFragmentByTag(TAG_MOVIE_LIST) as? MovieListFragment ?: MovieListFragment().also { addFragment(it, TAG_MOVIE_LIST) }
                    replaceFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {
                    val fragment = supportFragmentManager.findFragmentByTag(TAG_MOVIE_LIST) as? SettingFragment ?: SettingFragment().also { addFragment(it, TAG_SETTING) }
                    replaceFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
        navigation.selectedItemId = R.id.action_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.commit {
            add(fragment, tag)
        }
    }

    companion object {
        private const val TAG_RESERVATION_LIST = "RESERVATION_LIST"
        private const val TAG_MOVIE_LIST = "MOVIE_LIST"
        private const val TAG_SETTING = "SETTING"
    }
}
