package woowacourse.movie.presenter.main

import androidx.fragment.app.Fragment
import woowacourse.movie.contract.main.MainContract
import woowacourse.movie.ui.fragment.FragmentType
import woowacourse.movie.ui.fragment.movielist.MovieListFragment
import woowacourse.movie.ui.fragment.reservationlist.ReservationListFragment
import woowacourse.movie.ui.fragment.settings.SettingsFragment

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
