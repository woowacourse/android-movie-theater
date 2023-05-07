package woowacourse.movie.feature.reservation.reserve.count

interface TicketCounterContract {
    interface View {
        fun setCountNumber(count: Int)
        fun showMinLimit()
        fun showMaxLimit()
    }

    interface Presenter {
        val countNumber: Int
        fun minus()
        fun plus()
        fun setCountState(countState: Int)
    }
}
