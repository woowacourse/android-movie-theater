package woowacourse.movie.view.seat

import woowacourse.movie.domain.model.seat.Seat

class SeatActionHandler(
    private val presenter: SeatContract.Presenter,
) : SeatActivity.Handler {
    override fun onClickSeat(coord: Seat) {
        presenter.changeSeat(coord)
    }
}
