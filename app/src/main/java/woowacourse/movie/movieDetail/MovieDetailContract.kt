package woowacourse.movie.movieDetail

import android.content.Intent
import woowacourse.movie.model.movieInfo.MovieInfo
import java.time.LocalDate

interface MovieDetailContract {
    interface View {
        fun initializeViews(movieInfo: MovieInfo)

        fun navigateToPurchaseConfirmation(intent: Intent)

        fun onTicketCountChanged(currentTicketNum: Int)

        fun showToast(message: String)

        fun updateDateAdapter(dates: List<String>)

        fun updateTimeAdapter(times: List<String>)
    }

    interface Presenter {
        fun load()

        fun getTicketNum(): Int

        fun onTicketPlusClicked()

        fun onTicketMinusClicked()


        fun updateTimeSpinner(date: String)

        fun generateDateRange(
            startDate: LocalDate,
            endDate: LocalDate,
        )
    }
}
