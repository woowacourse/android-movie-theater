package woowacourse.movie.mock

import woowacourse.movie.R
import woowacourse.movie.model.AdvertisementUiModel

object MockAdvertisementFactory {
    fun generateAdvertisement(): AdvertisementUiModel {
        return AdvertisementUiModel(R.drawable.advertisement, "https://www.naver.com")
    }
}
