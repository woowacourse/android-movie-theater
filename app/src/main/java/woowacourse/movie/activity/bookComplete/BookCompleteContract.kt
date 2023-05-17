package woowacourse.movie.activity.bookComplete

import woowacourse.movie.model.TicketData

interface BookCompleteContract {

    interface View {
        var presenter: Presenter
        fun setUpBookCompleteView(ticketData: TicketData)
        fun displayToastIfDummyData()
    }

    interface Presenter {
        val data: TicketData
        fun loadTicketData()
    }
}
