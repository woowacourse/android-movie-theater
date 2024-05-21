package woowacourse.movie.data.source

import woowacourse.movie.R
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd

class DummyAdvertisementDataSource : AdvertisementDataSource {
    override fun load(): ScreenAndAd.Advertisement = ScreenAndAd.Advertisement(0)

    override fun imageSrc(advertisementId: Int): Image.DrawableImage = Image.DrawableImage(R.drawable.advertisement)
}
