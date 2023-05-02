package woowacourse.domain.advertisement

import woowacourse.data.advertisement.AdvertisementDatabase
import woowacourse.data.advertisement.AdvertisementEntity

object AdvertisementRepository {
    fun getAdvertisements(): List<Advertisement> {
        return AdvertisementDatabase.advertisements.map { it.toAdvertisement() }
    }

    fun getAdvertisement(id: Long): Advertisement {
        return AdvertisementDatabase.selectAdvertisement(id).toAdvertisement()
    }

    private fun AdvertisementEntity.toAdvertisement(): Advertisement {
        return Advertisement(
            id = this.id,
            link = this.link,
        )
    }
}
