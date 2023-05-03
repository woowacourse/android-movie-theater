package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.AdvertisementDataSource
import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.domain.Advertisement

class AdvertisementRepository {
    private val advertisementDataSource: DataSource<Advertisement> = AdvertisementDataSource()

    fun requestAdvertisements(): List<Advertisement> {
        return advertisementDataSource.value
    }
}
