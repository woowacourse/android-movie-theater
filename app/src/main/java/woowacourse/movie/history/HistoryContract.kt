package woowacourse.movie.history

import woowacourse.movie.ticket.model.BookingMovieModel

interface HistoryContract {
    interface View {
        fun setUpHistoryData(history: List<BookingMovieModel>)
    }

    interface Presenter {
        fun getHistory(): List<BookingMovieModel>
    }
}
