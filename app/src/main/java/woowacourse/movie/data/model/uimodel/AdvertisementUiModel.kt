package woowacourse.movie.data.model.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.data.model.itemmodel.AdvertisementItemModel

data class AdvertisementUiModel(
    @DrawableRes val image: Int,
    val url: String,
) : UiModel {
    fun toItemModel(onClick: (AdvertisementUiModel) -> Unit): AdvertisementItemModel {
        return AdvertisementItemModel(this, onClick)
    }
}
