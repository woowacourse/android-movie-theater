package woowacourse.movie.feature.reservation.reserve.count

import replaced.reservation.TicketCount

class TicketCounterPresenter(
    val view: TicketCounterContract.View
) : TicketCounterContract.Presenter {

    private var _countNumber: Int = 1
        set(value) {
            field = value
            view.setCountNumber(value)
        }
    override val countNumber: Int
        get() = _countNumber

    override fun minus() {
        if (_countNumber <= TicketCount.MINIMUM) {
            view.showMinLimit()
            return
        }
        _countNumber -= 1
    }

    override fun plus() {
        if (_countNumber >= TicketCount.MAXIMUM) {
            view.showMaxLimit()
            return
        }
        _countNumber += 1
    }

    override fun setCountState(countState: Int) { _countNumber = countState }
}
