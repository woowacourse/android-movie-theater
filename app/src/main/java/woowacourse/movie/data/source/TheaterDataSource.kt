package woowacourse.movie.data.source

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters

interface TheaterDataSource {
    fun loadAll(): Theaters

    fun findById(id: Int): Theater
}
