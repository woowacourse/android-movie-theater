package woowacourse.movie.presentation.result

import woowacourse.movie.domain.model.movie.MovieTicket

interface BookingResultContract {
    interface View {
        fun showTicket(ticket: MovieTicket)
    }

    interface Presenter {
        fun onViewCreated()
    }
}
