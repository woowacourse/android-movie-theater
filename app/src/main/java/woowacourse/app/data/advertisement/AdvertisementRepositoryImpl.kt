package woowacourse.app.data.advertisement

import woowacourse.app.data.advertisement.AdvertisementMapper.toAdvertisement
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository

class AdvertisementRepositoryImpl : AdvertisementRepository {
    override fun getAdvertisements(): List<Advertisement> {
        return AdvertisementDatabase.advertisements.map { it.toAdvertisement() }
    }

    override fun getAdvertisement(id: Long): Advertisement? {
        return AdvertisementDatabase.selectAdvertisement(id)?.toAdvertisement()
    }
}
