package com.woowacourse.data.datasource.history.local

import com.woowacourse.data.database.reservation.history.dao.ReservationDao
import com.woowacourse.data.datasource.history.HistoryDataSource
import com.woowacourse.data.model.DataReservation

class LocalHistoryDataSource(private val reservationDao: ReservationDao) : HistoryDataSource {
    override fun getAll(): List<DataReservation> =
        reservationDao.getAll()

    override fun add(item: DataReservation) {
        reservationDao.add(item)
    }
}
