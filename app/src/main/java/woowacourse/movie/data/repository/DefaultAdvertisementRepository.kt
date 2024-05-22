package woowacourse.movie.data.repository

import woowacourse.movie.data.source.AdvertisementDataSource
import woowacourse.movie.domain.model.ScreenAndAd

class DefaultAdvertisementRepository(
    private val advertisementDataSource: AdvertisementDataSource,
) : AdvertisementRepository {
    override fun loadAdvertisement(): ScreenAndAd.Advertisement {
        val advertisementData = advertisementDataSource.load()
        val advertisementImage = advertisementDataSource.imageSrc(advertisementData.advertisementId)
        return ScreenAndAd.Advertisement(
            id = advertisementData.advertisementId,
            content = advertisementData.content,
            image = advertisementImage,
        )
    }
}
