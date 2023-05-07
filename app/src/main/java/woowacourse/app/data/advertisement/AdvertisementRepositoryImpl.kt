package woowacourse.app.data.advertisement

import woowacourse.app.data.advertisement.AdvertisementMapper.toAdvertisement
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository

class AdvertisementRepositoryImpl(private val advertisementDataSource: AdvertisementDataSource) :
    AdvertisementRepository {
    override fun getAdvertisements(): List<Advertisement> {
        return advertisementDataSource.getAdvertisementEntities().map { it.toAdvertisement() }
    }

    override fun getAdvertisement(id: Long): Advertisement? {
        return advertisementDataSource.getAdvertisementEntity(id)?.toAdvertisement()
    }

    override fun addAdvertisement(link: String): Advertisement {
        val advertisementEntity = advertisementDataSource.addAdvertisementEntity(link)
        return advertisementEntity.toAdvertisement()
    }
}
