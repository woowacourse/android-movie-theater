package woowacourse.movie.list.contract

import androidx.fragment.app.Fragment
import woowacourse.movie.ticket.model.DbTicket

interface HomeContract {
    interface View {
        fun showFragment(fragment: Fragment)
    }

    interface Presenter {
        fun setBottomNavFragment(itemId: Int)
        fun storeDbTickets(dbTickets: List<DbTicket>)
    }
}
