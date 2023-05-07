package woowacourse.domain.advertisement

interface AdvertisementRepository {
    fun getAdvertisements(): List<Advertisement>

    fun getAdvertisement(id: Long): Advertisement?

    fun addAdvertisement(link: String): Advertisement
}
