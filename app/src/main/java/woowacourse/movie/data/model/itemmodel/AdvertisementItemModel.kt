package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.AdvertisementUiModel

data class AdvertisementItemModel(
    val advertisementUiModel: AdvertisementUiModel,
    val onClick: (AdvertisementUiModel) -> Unit
)
