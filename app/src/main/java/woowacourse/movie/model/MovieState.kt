package woowacourse.movie.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.common.itemModel.MovieItemModel
import java.time.LocalDate

@Parcelize
data class MovieState(
    val id: Int,
    @DrawableRes val imgId: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val description: String
) : Parcelable {
    fun toItemModel(onClick: (position: Int) -> Unit): MovieItemModel {
        return MovieItemModel(this, onClick)
    }
}
