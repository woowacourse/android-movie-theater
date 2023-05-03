package com.woowacourse.data.datasource.history.local

import com.woowacourse.data.database.reservation.history.dao.HistoryDao
import com.woowacourse.data.datasource.history.HistoryDataSource
import com.woowacourse.data.model.DataReservation

class LocalHistoryDataSource(private val historyDao: HistoryDao) : HistoryDataSource {
    override fun getAll(): List<DataReservation> =
        historyDao.getAll()

    override fun add(item: DataReservation) {
        historyDao.add(item)
    }
}
