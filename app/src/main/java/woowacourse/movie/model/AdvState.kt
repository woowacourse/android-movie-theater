package woowacourse.movie.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel

@Parcelize
data class AdvState(
    @DrawableRes
    val imgId: Int,
    val advId: Int,
    val advDescription: String
) : Parcelable {
    fun toItemModel(onClick: (advState: AdvState) -> Unit): AdvItemModel {
        return AdvItemModel(this, onClick)
    }
}
