package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.dataSource.AdvertisementDataSource
import woowacourse.movie.domain.dataSource.DataSource

class AdvertisementRepository {
    private val advertisementDataSource: DataSource<Advertisement> = AdvertisementDataSource()

    fun requestAdvertisements(): List<Advertisement> {
        return advertisementDataSource.value
    }
}
