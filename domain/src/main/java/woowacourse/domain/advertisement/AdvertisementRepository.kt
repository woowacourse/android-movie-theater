package woowacourse.domain.advertisement

import woowacourse.domain.util.CgvResult

interface AdvertisementRepository {
    fun getAdvertisements(): List<Advertisement>

    fun getAdvertisement(id: Long): CgvResult<Advertisement>

    fun addAdvertisement(link: String): Advertisement
}
