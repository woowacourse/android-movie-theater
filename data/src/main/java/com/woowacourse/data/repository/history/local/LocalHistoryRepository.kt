package com.woowacourse.data.repository.history.local

import com.woowacourse.data.datasource.history.HistoryDataSource
import com.woowacourse.data.mapper.toData
import com.woowacourse.data.mapper.toDomain
import woowacourse.movie.domain.model.repository.HistoryRepository
import woowacourse.movie.domain.model.reservation.DomainReservation

class LocalHistoryRepository(private val dataSource: HistoryDataSource) : HistoryRepository {
    override fun getAll(): List<DomainReservation> =
        dataSource.getAll().map { it.toDomain() }

    override fun add(item: DomainReservation) {
        dataSource.add(item.toData())
    }
}
