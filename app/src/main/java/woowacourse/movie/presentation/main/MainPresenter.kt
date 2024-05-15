package woowacourse.movie.presentation.main

import woowacourse.movie.R
import woowacourse.movie.presentation.main.history.ReservationHistoryFragment
import woowacourse.movie.presentation.main.home.HomeFragment
import woowacourse.movie.presentation.main.setting.SettingFragment

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun onBottomNavItemSelected(itemId: Int) {
        when (itemId) {
            R.id.home_fragment_item -> view.showFragment(HomeFragment())
            R.id.reservation_details_fragment_item -> view.showFragment(ReservationHistoryFragment())
            R.id.setting_fragment_item -> view.showFragment(SettingFragment())
        }
    }
}
