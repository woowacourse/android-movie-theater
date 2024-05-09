package woowacourse.movie.list.presenter

import woowacourse.movie.R
import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.list.contract.HomeContract
import woowacourse.movie.list.view.MovieListFragment
import woowacourse.movie.list.view.ReservationHistoryFragment
import woowacourse.movie.list.view.SettingFragment
import woowacourse.movie.ticket.model.DbTicket

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun setBottomNavFragment(itemId: Int) {
        when (itemId) {
            R.id.home_fragment_item -> view.showFragment(MovieListFragment())
            R.id.reservation_details_fragment_item -> view.showFragment(ReservationHistoryFragment())
            R.id.setting_fragment_item -> view.showFragment(SettingFragment())
        }
    }

    override fun storeDbTickets(dbTickets: List<DbTicket>) {
        CommonDataSource.dbTickets = dbTickets
    }
}
