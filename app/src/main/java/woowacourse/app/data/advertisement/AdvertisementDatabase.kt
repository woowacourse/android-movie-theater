package woowacourse.app.data.advertisement

object AdvertisementDatabase {
    val advertisements = listOf(
        AdvertisementEntity(0, "https://woowacourse.github.io/"),
    )

    fun selectAdvertisement(id: Long): AdvertisementEntity? {
        return advertisements.find { it.id == id }
    }
}
