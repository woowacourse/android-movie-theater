package woowacourse.movie.view.reservationresult

interface ReservationResultContract {
    interface Presenter {
        fun updatePrice()
    }

    interface View {
        val presenter: Presenter
        fun setPriceTextView(price: Int)
    }
}
