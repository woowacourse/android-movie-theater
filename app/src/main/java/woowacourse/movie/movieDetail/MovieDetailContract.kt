package woowacourse.movie.movieDetail

import android.content.Intent
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title

interface MovieDetailContract {
    interface View {
        fun navigateToPurchaseConfirmation(intent: Intent)

        fun onTicketCountChanged(ticketNum: Int)

        fun showTicketMessage(message: String)


        fun showTitle(title:Title)
        fun showRunningTime(runningTime: RunningTime)
        fun showSynopsis(synopsis: Synopsis)
        fun showReleaseDate(movieDate: MovieDate)
        fun updateDateAdapter(dates: List<String>)

        fun updateTimeAdapter(times: List<String>)
    }

    interface Presenter {
        fun increaseTicketCount()

        fun decreaseTicketCount()

        fun updateRunMovieTimes()

        fun loadRunMovieDateRange()
        fun loadMovieInfo()
    }
}
