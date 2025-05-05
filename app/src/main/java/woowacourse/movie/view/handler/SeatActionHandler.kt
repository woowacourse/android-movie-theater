package woowacourse.movie.view.handler

import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.view.home.seat.SeatActivity
import woowacourse.movie.view.home.seat.SeatContract

class SeatActionHandler(
    private val presenter: SeatContract.Presenter,
) : SeatActivity.Handler {
    override fun onClickSeat(coord: Seat) {
        presenter.changeSeat(coord)
    }
}
