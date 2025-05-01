package woowacourse.movie.presentation.result

import woowacourse.movie.domain.model.movie.MovieTicket

class BookingResultPresenter(
    private val view: BookingResultContract.View,
    private val ticket: MovieTicket,
) : BookingResultContract.Presenter {
    override fun onViewCreated() {
        view.showTicket(ticket)
    }
}
