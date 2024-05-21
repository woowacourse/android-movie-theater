package woowacourse.movie.data.source

import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.ScreenAndAd

interface AdvertisementDataSource {
    fun load(): ScreenAndAd.Advertisement

    fun imageSrc(advertisementId: Int): Image.DrawableImage
}
