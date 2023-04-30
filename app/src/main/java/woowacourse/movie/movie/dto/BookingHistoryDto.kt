package woowacourse.movie.movie.dto

import woowacourse.movie.movie.dto.movie.BookingMovieEntity

object BookingHistoryDto : java.io.Serializable {

    private val bookingHistory = mutableListOf<BookingMovieEntity>()

    fun add(movie: BookingMovieEntity) {
        bookingHistory.add(movie)
    }

    fun getHistory(): List<BookingMovieEntity> = bookingHistory.toList()
}
