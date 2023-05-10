package woowacourse.movie.view.reservationresult

import woowacourse.movie.model.TicketsUiModel

interface ReservationResultContract {
    interface Presenter {
        fun calculateTicketsPrice(ticketsUiModel: TicketsUiModel)
    }

    interface View {
        fun setPriceText(price: Int)
    }
}
