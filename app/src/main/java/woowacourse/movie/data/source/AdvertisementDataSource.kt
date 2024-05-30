package woowacourse.movie.data.source

import woowacourse.movie.data.model.AdvertisementData
import woowacourse.movie.domain.model.Image

interface AdvertisementDataSource {
    fun load(): AdvertisementData

    fun imageSrc(advertisementId: Int): Image.DrawableImage
}
