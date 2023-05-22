package woowacourse.movie.data.mock

import woowacourse.movie.R
import woowacourse.movie.data.model.uimodel.AdvertisementUIModel

object MockAdvertisementFactory {
    fun generateAdvertisement(): AdvertisementUIModel {
        return AdvertisementUIModel(R.drawable.advertisement, "https://www.naver.com")
    }
}
