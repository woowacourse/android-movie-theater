package woowacourse.movie.data.repository

import woowacourse.movie.data.source.AdvertisementDataSource
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd

class FakeAdvertisementDataSource : AdvertisementDataSource {
    override fun load(): ScreenAndAd.Advertisement = ScreenAndAd.Advertisement(id = 0)

    override fun imageSrc(advertisementId: Int): Image.DrawableImage = Image.DrawableImage(1)
}
