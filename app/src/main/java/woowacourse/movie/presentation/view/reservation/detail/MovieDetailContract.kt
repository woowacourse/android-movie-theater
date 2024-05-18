package woowacourse.movie.presentation.view.reservation.detail

import woowacourse.movie.presentation.uimodel.MovieUiModel

interface MovieDetailContract {
    interface View {
        fun showMovieDetails(movieUiModel: MovieUiModel)

        fun setScreeningDates(
            dates: List<String>,
            defaultDataIndex: Int,
        )

        fun updateScreeningTimes(
            times: List<String>,
            defaultDataIndex: Int,
        )

        fun updateReservationCount(count: Int)

        fun moveToSeatSelection(
            reservationCount: Int,
            title: String,
        )
    }

    interface Presenter {
        fun attachView(view: View)

        fun detachView()

        fun onViewSetUp()

        fun loadMovieDetails()

        fun loadScreeningDates()

        fun loadScreeningTimes(date: String)

        fun selectDate(date: String)

        fun selectTime(time: String)

        fun minusReservationCount()

        fun plusReservationCount()

        fun initReservationCount(count: Int)

        fun onReserveButtonClicked()
    }

    interface ViewActions {
        fun onPlusButtonClicked()

        fun onMinusButtonClicked()

        fun onReserveButtonClicked()
    }
}
