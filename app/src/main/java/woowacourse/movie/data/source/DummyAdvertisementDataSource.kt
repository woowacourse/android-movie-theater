package woowacourse.movie.data.source

import woowacourse.movie.R
import woowacourse.movie.data.model.AdvertisementData
import woowacourse.movie.domain.model.Image

class DummyAdvertisementDataSource : AdvertisementDataSource {
    override fun load(): AdvertisementData = AdvertisementData(0, "우아한테크코스")

    override fun imageSrc(advertisementId: Int): Image.DrawableImage = Image.DrawableImage(R.drawable.advertisement)
}
