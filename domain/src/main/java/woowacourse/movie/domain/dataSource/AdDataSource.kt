package woowacourse.movie.domain.dataSource

import woowacourse.movie.domain.model.Advertisement

interface AdDataSource {
    fun getData(): List<Advertisement>
}
