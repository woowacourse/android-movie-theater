package woowacourse.movie.view.main

import androidx.fragment.app.Fragment
import woowacourse.movie.model.FragmentType
import woowacourse.movie.view.movieList.MovieListFragment
import woowacourse.movie.view.reservationList.ReservationListFragment
import woowacourse.movie.view.setting.SettingsFragment

class MainPresenter(private val view: MainContract.View) : MainContract.Present {
    override fun createFragment(type: FragmentType): Fragment =
        when (type) {
            FragmentType.RESERVATION_LIST -> ReservationListFragment()
            FragmentType.HOME -> MovieListFragment()
            FragmentType.SETTING -> SettingsFragment()
        }

    override fun setFragment(type: FragmentType) {
        view.showFragment(type)
    }
}
