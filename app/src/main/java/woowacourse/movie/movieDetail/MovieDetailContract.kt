package woowacourse.movie.movieDetail

import android.content.Intent
import woowacourse.movie.model.movieInfo.MovieInfo

interface MovieDetailContract {
    interface View {
        fun navigateToPurchaseConfirmation(intent: Intent)

        fun onTicketCountChanged()

        fun showToast(message: String)

        fun updateDateAdapter(dates: List<String>)

        fun updateTimeAdapter(times: List<String>)
    }

    interface Presenter {

        fun load(movie: MovieInfo)

        fun onTicketPlusClicked()

        fun onTicketMinusClicked()

        fun updateTimeSpinner(times: List<String>)

        fun generateDateRange()
    }
}
