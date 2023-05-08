package woowacourse.movie.view.moviemain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.movielist.MovieListFragment.Companion.TAG_MOVIE_LIST
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment.Companion.TAG_RESERVATION_LIST
import woowacourse.movie.view.moviemain.setting.SettingFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment.Companion.TAG_SETTING

class MovieMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_main)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation_view)

        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation_list -> {
                    val fragment = ReservationListFragment.of(supportFragmentManager)
                    replaceFragment(fragment, TAG_RESERVATION_LIST)
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    val fragment = MovieListFragment.of(supportFragmentManager)
                    replaceFragment(fragment, TAG_MOVIE_LIST)
                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {
                    val fragment = SettingFragment.of(supportFragmentManager)
                    replaceFragment(fragment, TAG_SETTING)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
        navigation.selectedItemId = R.id.action_home
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment, tag)
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.commit {
            add(fragment, tag)
        }
    }
}
