package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.AdvertisementDataSource
import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.domain.mock.AdvertisementPolicyMock

class AdvertisementRepository {
    private val advertisementDataSource: DataSource<Advertisement> = AdvertisementDataSource()

    fun requestAdvertisements(): List<Advertisement> {
        return advertisementDataSource.value
    }

    fun requestAdvertisementPolicy(): AdvertisementPolicy {
        return AdvertisementPolicyMock.createAdvertisementPolicy()
    }
}
