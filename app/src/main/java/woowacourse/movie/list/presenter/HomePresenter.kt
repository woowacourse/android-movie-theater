package woowacourse.movie.list.presenter

import woowacourse.movie.R
import woowacourse.movie.list.view.HomeActivityContract
import woowacourse.movie.list.view.MovieListFragment
import woowacourse.movie.list.view.ReservationHistoryFragment
import woowacourse.movie.list.view.SettingFragment

class HomePresenter(private val view: HomeActivityContract.View) : HomeActivityContract.Presenter {
    override fun onBottomNavItemSelected(itemId: Int) {
        when (itemId) {
            R.id.home_fragment_item -> view.showFragment(MovieListFragment())
            R.id.reservation_details_fragment_item -> view.showFragment(ReservationHistoryFragment())
            R.id.setting_fragment_item -> view.showFragment(SettingFragment())
        }
    }
}
