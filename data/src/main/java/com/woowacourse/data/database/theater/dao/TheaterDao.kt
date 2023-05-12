package com.woowacourse.data.database.theater.dao

import com.woowacourse.data.model.DataMovieDate
import com.woowacourse.data.model.DataTheater

interface TheaterDao {
    fun getAllByMovieId(id: Int, date: DataMovieDate): List<DataTheater>
}
