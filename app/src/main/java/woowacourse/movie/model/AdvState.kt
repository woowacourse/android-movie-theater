package woowacourse.movie.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.ui.itemModel.AdvItemModel

@Parcelize
data class AdvState(
    @DrawableRes
    val imgId: Int,
    val advDescription: String
) : Parcelable {
    fun toItemModel(onClick: (position: Int) -> Unit): AdvItemModel {
        return AdvItemModel(this, onClick)
    }
}
