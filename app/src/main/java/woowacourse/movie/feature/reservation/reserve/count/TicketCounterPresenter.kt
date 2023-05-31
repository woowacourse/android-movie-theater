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
        if (!TicketCount.isValidate(_countNumber)) {
            view.showLimitMin()
            return
        }
        _countNumber -= 1
    }

    override fun plus() {
        if (!TicketCount.isValidate(_countNumber)) {
            view.showLimitMax()
            return
        }
        _countNumber += 1
    }

    override fun setCountState(countState: Int) { _countNumber = countState }
}
