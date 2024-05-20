package woowacourse.movie.domain.repository

import woowacourse.movie.R
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd

class DummyAdvertisement : AdRepository {
    override fun load(): ScreenAndAd.Advertisement = ScreenAndAd.Advertisement(0)

    override fun imageSrc(advertisementId: Int): Image.DrawableImage = Image.DrawableImage(R.drawable.advertisement)
}

interface AdRepository {
    fun load(): ScreenAndAd.Advertisement

    fun imageSrc(advertisementId: Int): Image.DrawableImage
}
