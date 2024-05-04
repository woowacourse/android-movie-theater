package woowacourse.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.home.view.MovieHomeFragment
import woowacourse.movie.reservationlist.view.ReservationListFragment
import woowacourse.movie.setting.view.SettingFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.home_item
            addFragment(MovieHomeFragment(), MOVIE_HOME_FRAGMENT_TAG)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.reservation_list_item -> {
                    replaceFragment(ReservationListFragment(), RESERVATION_LIST_FRAGMENT_TAG)
                    true
                }

                R.id.home_item -> {
                    replaceFragment(MovieHomeFragment(), MOVIE_HOME_FRAGMENT_TAG)
                    true
                }

                R.id.setting_item -> {
                    replaceFragment(SettingFragment(), SETTING_FRAGMENT_TAG)
                    true
                }

                else -> false
            }
        }
    }

    private fun addFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment, tag)
        }
    }

    private fun replaceFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment, tag)
        }
    }

    companion object {
        private const val RESERVATION_LIST_FRAGMENT_TAG = "reservationListFragment"
        private const val MOVIE_HOME_FRAGMENT_TAG = "movieHomeFragment"
        private const val SETTING_FRAGMENT_TAG = "settingFragment"
    }
}
