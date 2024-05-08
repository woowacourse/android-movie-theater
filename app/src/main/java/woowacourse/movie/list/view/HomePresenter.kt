package woowacourse.movie.list.view

import woowacourse.movie.R

class HomePresenter(private val view: HomeActivityContract.View) : HomeActivityContract.Presenter {
    override fun onBottomNavItemSelected(itemId: Int) {
        when (itemId) {
            R.id.home_fragment_item -> view.showFragment(HomeFragment())
            R.id.reservation_details_fragment_item -> view.showFragment(ReservationHistoryFragment())
            R.id.setting_fragment_item -> view.showFragment(SettingFragment())
        }
    }
}
