package woowacourse.movie.data.repository

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters

interface TheaterRepository {
    fun loadAll(): Theaters

    fun findById(id: Int): Theater
}
