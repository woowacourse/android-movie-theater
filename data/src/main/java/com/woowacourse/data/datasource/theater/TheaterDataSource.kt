package com.woowacourse.data.datasource.theater

import com.woowacourse.data.model.DataMovieDate
import com.woowacourse.data.model.DataTheater

interface TheaterDataSource {
    fun getAllByMovieId(
        id: Int,
        date: DataMovieDate,
    ): List<DataTheater>
}
