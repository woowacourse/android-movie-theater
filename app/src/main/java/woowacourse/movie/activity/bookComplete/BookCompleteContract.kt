package woowacourse.movie.activity.bookComplete

import woowacourse.movie.model.TicketData

interface BookCompleteContract {

    interface View {
        var presenter: Presenter
        fun initView(ticketData: TicketData)
        fun displayToastIfDummyData()
    }

    interface Presenter {
        val data: TicketData
        fun initView()
    }
}
