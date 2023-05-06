package woowacourse.movie.data.mock

import woowacourse.movie.R
import woowacourse.movie.data.model.uimodel.AdvertisementUiModel

object MockAdvertisementFactory {
    fun generateAdvertisement(): AdvertisementUiModel {
        return AdvertisementUiModel(R.drawable.advertisement, "https://www.naver.com")
    }
}
