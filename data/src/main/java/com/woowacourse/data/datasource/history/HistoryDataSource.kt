package com.woowacourse.data.datasource.history

import com.woowacourse.data.model.DataReservation

interface HistoryDataSource {
    fun getAll(): List<DataReservation>
    fun add(item: DataReservation)
}
