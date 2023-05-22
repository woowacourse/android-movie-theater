package woowacourse.movie.feature.reservation.reserve.count

interface TicketCounterContract {
    interface View {
        fun setCountNumber(count: Int)
        fun showLimitMin()
        fun showLimitMax()
    }

    interface Presenter {
        val countNumber: Int
        fun minus()
        fun plus()
        fun setCountState(countState: Int)
    }
}
