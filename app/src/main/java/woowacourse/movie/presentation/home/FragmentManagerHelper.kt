package woowacourse.movie.presentation.home

import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.presentation.homefragments.movieList.MovieListFragment
import woowacourse.movie.presentation.homefragments.reservation.ReservationFragment
import woowacourse.movie.presentation.homefragments.setting.SettingFragment

class FragmentManagerHelper(activity: HomeActivity, private val containerId: Int) {
    private val fragmentManager = activity.supportFragmentManager
    private val fragmentsMap =
        mapOf(
            R.id.action_home to MovieListFragment(),
            R.id.action_reservation_list to ReservationFragment(),
            R.id.action_settings to SettingFragment(),
        )

    init {
        val firstFragment = fragmentsMap[R.id.action_home] ?: MovieListFragment()
        fragmentManager.commit {
            add(containerId, firstFragment)
        }
    }

    fun replace(menuId: Int) {
        fragmentsMap[menuId]?.let {
            fragmentManager.commit {
                replace(containerId, it)
            }
        }
    }
}
