package woowacourse.movie.movieDetail

import android.content.Intent

interface MovieDetailContract {
    interface View {
        fun navigateToPurchaseConfirmation(intent: Intent)

        fun onTicketCountChanged()

        fun showToast(message: String)

        fun updateDateAdapter(dates: List<String>)

        fun updateTimeAdapter(times: List<String>)
    }

    interface Presenter {
        fun onTicketPlusClicked()

        fun onTicketMinusClicked()

        fun updateTimes()

        fun generateDateRange()
    }
}
