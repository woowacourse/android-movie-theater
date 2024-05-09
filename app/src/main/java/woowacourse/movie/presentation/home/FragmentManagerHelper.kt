package woowacourse.movie.presentation.home

import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.presentation.homefragments.movieList.MovieListFragment
import woowacourse.movie.presentation.homefragments.reservation.ReservationFragment
import woowacourse.movie.presentation.homefragments.setting.SettingFragment

class FragmentManagerHelper(activity: HomeActivity, private val containerId: Int) {
    private val fragmentManager = activity.supportFragmentManager
    private val movieListFragment = MovieListFragment()
    private val reservationFragment = ReservationFragment()
    private val settingFragment = SettingFragment()

    init {
        fragmentManager.commit {
            add(containerId, movieListFragment)
        }
    }

    fun replace(
        menuId: Int,
        isEnabled: Boolean,
    ) {
        val target =
            when (menuId) {
                R.id.action_home -> movieListFragment
                R.id.action_reservation_list -> reservationFragment
                R.id.action_settings -> {
                    settingFragment.apply {
                        arguments = SettingFragment.createBundle(isEnabled)
                    }
                }
                else -> movieListFragment
            }
        fragmentManager.commit {
            replace(containerId, target)
        }
    }
}
