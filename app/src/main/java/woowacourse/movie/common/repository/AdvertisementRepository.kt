package woowacourse.movie.common.repository

import woowacourse.movie.dataSource.AdvertisementDataSource
import woowacourse.movie.dataSource.DataSource
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
