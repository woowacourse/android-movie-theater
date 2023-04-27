package woowacourse.movie.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdvState(
    @DrawableRes
    val imgId: Int,
    val advDescription: String
) : Parcelable
