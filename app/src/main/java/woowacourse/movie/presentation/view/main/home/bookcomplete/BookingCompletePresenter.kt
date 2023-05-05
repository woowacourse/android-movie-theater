package woowacourse.movie.presentation.view.main.home.bookcomplete

import woowacourse.movie.presentation.model.ReservationResult

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val reservation: ReservationResult?
) : BookingCompleteContract.Presenter {
    override fun onCreate() {
        if (reservation == null) {
            view.finishErrorView()
            return
        }
        view.initView(reservation)
    }

}