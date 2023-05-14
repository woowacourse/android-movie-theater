package woowacourse.movie.database

import woowacourse.movie.movie.MovieBookingSeatInfo

interface BookingHistoryRepository {
    fun getAll(): List<MovieBookingSeatInfo>
    fun insert(movieBookingSeatInfo: MovieBookingSeatInfo)
    fun clear()
    fun isEmpty(): Boolean
    fun close()
}
