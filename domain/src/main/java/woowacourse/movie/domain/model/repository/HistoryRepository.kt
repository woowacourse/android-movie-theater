package woowacourse.movie.domain.model.repository

import woowacourse.movie.domain.model.reservation.Reservation

interface HistoryRepository {
    fun getAll(): List<Reservation>
    fun add(item: Reservation)
}
