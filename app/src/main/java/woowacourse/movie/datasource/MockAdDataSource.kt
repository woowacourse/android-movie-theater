package woowacourse.movie.datasource

import woowacourse.movie.domain.dataSource.AdDataSource
import woowacourse.movie.domain.model.Advertisement
import woowacourse.movie.domain.model.Image

class MockAdDataSource : AdDataSource {
    override fun getData(): List<Advertisement> {
        return listOf(
            Advertisement(
                Image(4)
            )
        )
    }
}
