package woowacourse.movie.data.repository

import woowacourse.movie.data.model.AdvertisementData
import woowacourse.movie.data.source.AdvertisementDataSource
import woowacourse.movie.domain.model.Image

class FakeAdvertisementDataSource : AdvertisementDataSource {
    override fun load(): AdvertisementData = AdvertisementData(0, "우아한테크코스")

    override fun imageSrc(advertisementId: Int): Image.DrawableImage = Image.DrawableImage(1)
}
