package woowacourse.movie.data.model.uimodel

import androidx.annotation.DrawableRes
import woowacourse.movie.data.model.itemmodel.AdvertisementItemModel

data class AdvertisementUIModel(
    @DrawableRes val image: Int,
    val url: String,
) : UIModel {
    fun toItemModel(onClick: (AdvertisementUIModel) -> Unit): AdvertisementItemModel {
        return AdvertisementItemModel(this, onClick)
    }
}
