package woowacourse.movie.feature.reservation.confirm

import woowacourse.movie.model.TicketsState

class TicketConfirmPresenter(
    private val view: TicketsConfirmContract.View,
    private val tickets: TicketsState?
) : TicketsConfirmContract.Presenter {

    override fun loadContents() {
        if (tickets == null) {
            view.showContentError()
            view.finishActivity()
            return
        }
        view.setViewContents(tickets)
    }
}
