package woowacourse.movie.data

import woowacourse.movie.model.AdvState

interface AdvRepository {
    fun allAdv(): List<AdvState>
}
