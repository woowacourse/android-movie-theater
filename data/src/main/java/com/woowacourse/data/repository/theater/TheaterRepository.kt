package com.woowacourse.data.repository.theater

import woowacourse.movie.domain.model.movie.DomainMovieDate
import woowacourse.movie.domain.model.theater.DomainTheater
import java.time.LocalDate

interface TheaterRepository {
    fun getAllByMovieId(
        id: Int,
        date: DomainMovieDate = DomainMovieDate(LocalDate.now()),
    ): List<DomainTheater>
}
