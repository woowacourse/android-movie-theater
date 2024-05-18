package woowacourse.movie.presentation.reservation.detail

interface MovieDetailContract {
    interface View {
        fun setScreeningDatesAndTimes(
            dates: List<String>,
            times: List<String>,
            defaultDataIndex: Int,
        )

        fun updateScreeningTimes(
            times: List<String>,
            defaultDataIndex: Int,
        )

        fun updateCount(count: Int)

        fun moveToSeatSelection(
            reservationCount: Int,
            title: String,
        )
    }

    interface Presenter {
        fun attachView(view: View)

        fun detachView()

        fun onViewSetUp()

        fun loadScreeningDates(movieId: Int)

        fun loadScreeningTimes(isWeekend: Boolean)

        fun selectDate(date: String)

        fun selectTime(time: String)

        fun minusReservationCount()

        fun plusReservationCount()

        fun initReservationCount(count: Int)

        fun onReserveButtonClicked()
    }
}
