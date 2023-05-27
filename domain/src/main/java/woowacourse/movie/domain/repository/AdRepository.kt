package woowacourse.movie.domain.repository

import woowacourse.movie.domain.dataSource.AdDataSource
import woowacourse.movie.domain.model.Advertisement

class AdRepository(private val dataSource: AdDataSource) {
    fun getData(): List<Advertisement> {
        return dataSource.getData()
    }
}
