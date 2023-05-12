package com.woowacourse.data.datasource.theater.local

import com.woowacourse.data.database.theater.dao.TheaterDao
import com.woowacourse.data.datasource.theater.TheaterDataSource
import com.woowacourse.data.model.DataMovieDate
import com.woowacourse.data.model.DataTheater

class LocalTheaterDataSource(private val dao: TheaterDao) : TheaterDataSource {
    override fun getAllByMovieId(id: Int, date: DataMovieDate): List<DataTheater> {
        return dao.getAllByMovieId(id, date)
    }
}
