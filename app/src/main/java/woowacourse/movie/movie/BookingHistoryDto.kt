package woowacourse.movie.movie

import woowacourse.movie.movie.dto.BookingMovieDto

object BookingHistoryDto : java.io.Serializable {

    private val bookingHistory = mutableListOf<BookingMovieDto>()

    fun add(movie: BookingMovieDto) {
        bookingHistory.add(movie)
    }

    fun getHistory(): List<BookingMovieDto> = bookingHistory.toList()
}
