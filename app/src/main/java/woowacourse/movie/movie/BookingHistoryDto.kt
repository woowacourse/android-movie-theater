package woowacourse.movie.movie

import woowacourse.movie.movie.dto.BookingMovieDto

object BookingHistoryDto {

    private val bookingHistory = mutableListOf<BookingMovieDto>()

    fun add(movie: BookingMovieDto) {
        bookingHistory.add(movie)
    }

    fun getHistory(): List<BookingMovieDto> = bookingHistory.toList()
}
