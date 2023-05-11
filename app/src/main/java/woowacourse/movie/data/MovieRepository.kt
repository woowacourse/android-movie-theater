package woowacourse.movie.data

import woowacourse.movie.model.BookingHistoryData

interface MovieRepository {

    fun loadData(): List<BookingHistoryData>

    fun addData(bookingHistoryData: BookingHistoryData)
}
