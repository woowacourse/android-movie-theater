package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.AdvertisementUIModel

data class AdvertisementItemModel(
    val advertisementUiModel: AdvertisementUIModel,
    val onClick: (AdvertisementUIModel) -> Unit
)
