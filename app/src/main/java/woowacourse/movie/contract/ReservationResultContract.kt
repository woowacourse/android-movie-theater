package woowacourse.movie.contract

interface ReservationResultContract {
    interface Presenter {
        fun updatePrice()
    }

    interface View {
        val presenter: Presenter
        fun setPriceTextView(price: Int)
    }
}
