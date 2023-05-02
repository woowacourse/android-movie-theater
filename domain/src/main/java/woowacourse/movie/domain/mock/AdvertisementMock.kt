package woowacourse.movie.domain.mock

import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.Image

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
