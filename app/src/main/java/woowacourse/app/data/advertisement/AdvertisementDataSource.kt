package woowacourse.app.data.advertisement

interface AdvertisementDataSource {
    fun getAdvertisementEntities(): List<AdvertisementEntity>
    fun getAdvertisementEntity(advertisementId: Long): AdvertisementEntity?
    fun addAdvertisementEntity(link: String): AdvertisementEntity
}
