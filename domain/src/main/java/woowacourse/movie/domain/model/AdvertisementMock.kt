package woowacourse.movie.domain.model

object AdvertisementMock {
    fun createAdvertisement(): Advertisement = Advertisement(
        Image(4)
    )

    fun createAdvertisements(): List<Advertisement> = listOf(
        Advertisement(
            Image(4)
        )
    )
}
