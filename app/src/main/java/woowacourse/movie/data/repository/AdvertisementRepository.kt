package woowacourse.movie.data.repository

import woowacourse.movie.domain.model.ScreenAndAd

interface AdvertisementRepository {
    fun loadAdvertisement(): ScreenAndAd.Advertisement
}
