package woowacourse.movie.feature.reservation.confirm

import woowacourse.movie.model.TicketsState

interface TicketsConfirmContract {
    interface View {
        fun setViewContents(tickets: TicketsState)
        fun finishActivity()
        fun showContentError()
    }

    interface Presenter {
        fun loadContents()
    }
}
