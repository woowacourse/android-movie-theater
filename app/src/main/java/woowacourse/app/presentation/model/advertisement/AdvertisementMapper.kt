package woowacourse.app.presentation.model.advertisement

import woowacourse.domain.advertisement.Advertisement
import woowacourse.movie.R

object AdvertisementMapper {
    fun Advertisement.toUiModel(): AdvertisementUiModel {
        return AdvertisementUiModel(
            id = this.id,
            link = this.link,
            image = R.drawable.woowa_advertisement,
        )
    }

    fun List<Advertisement>.toUiAdvertisements(): List<AdvertisementUiModel> =
        map { it.toUiModel() }
}
