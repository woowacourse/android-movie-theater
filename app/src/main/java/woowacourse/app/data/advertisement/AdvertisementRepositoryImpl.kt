package woowacourse.app.data.advertisement

import woowacourse.app.data.advertisement.AdvertisementMapper.toAdvertisement
import woowacourse.domain.advertisement.Advertisement
import woowacourse.domain.advertisement.AdvertisementRepository
import woowacourse.domain.util.CgvError.DataSourceError.DataSourceNoSuchId
import woowacourse.domain.util.CgvResult

class AdvertisementRepositoryImpl(private val advertisementDataSource: AdvertisementDataSource) :
    AdvertisementRepository {
    override fun getAdvertisements(): List<Advertisement> {
        return advertisementDataSource.getAdvertisementEntities().map { it.toAdvertisement() }
    }

    override fun getAdvertisement(id: Long): CgvResult<Advertisement> {
        val advertisementEntity = advertisementDataSource.getAdvertisementEntity(id)
            ?: return CgvResult.Failure(DataSourceNoSuchId())
        return CgvResult.Success(advertisementEntity.toAdvertisement())
    }

    override fun addAdvertisement(link: String): Advertisement {
        val advertisementEntity = advertisementDataSource.addAdvertisementEntity(link)
        return advertisementEntity.toAdvertisement()
    }
}
