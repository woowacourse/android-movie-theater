package com.woowacourse.data.repository.theater.local

import com.woowacourse.data.datasource.theater.TheaterDataSource
import com.woowacourse.data.mapper.toData
import com.woowacourse.data.mapper.toDomain
import woowacourse.movie.domain.model.movie.DomainMovieDate
import woowacourse.movie.domain.model.repository.TheaterRepository
import woowacourse.movie.domain.model.theater.DomainTheater

class LocalTheaterRepository(private val dataSource: TheaterDataSource) : TheaterRepository {
    override fun getAllByMovieId(id: Int, date: DomainMovieDate): List<DomainTheater> =
        dataSource.getAllByMovieId(id, date.toData()).map {
            it.toDomain()
        }
}
