package woowacourse.movie.history.model

import woowacourse.movie.ticket.model.BookingMovieModel

object BookingHistoryUIModel : java.io.Serializable {

    private val bookingHistory = mutableListOf<BookingMovieModel>()

    fun add(movie: BookingMovieModel) {
        bookingHistory.add(movie)
    }

    fun deleteAll() {
        bookingHistory.clear()
    }

    fun getHistory(): List<BookingMovieModel> = bookingHistory.toList()
}
