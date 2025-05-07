package woowacourse.movie.ui.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MovieUiModel(
    @DrawableRes val imageSource: Int,
    val title: String,
    val screeningStartDate: LocalDate,
    val screeningEndDate: LocalDate,
    val runningTime: Int,
) : Parcelable
