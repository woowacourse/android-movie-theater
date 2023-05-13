package woowacourse.movie.dto

import woowacourse.movie.dto.movie.BookingMovieEntity

object BookingHistoryDto : java.io.Serializable {

    private val bookingHistory = mutableListOf<BookingMovieEntity>()

    fun add(movie: BookingMovieEntity) {
        bookingHistory.add(movie)
    }

    fun deleteAll() {
        bookingHistory.clear()
    }

    fun getHistory(): List<BookingMovieEntity> = bookingHistory.toList()
}
