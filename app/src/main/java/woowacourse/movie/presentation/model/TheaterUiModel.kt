package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheaterUiModel(
    val name: String,
    val totalScreeningTimes: Int,
) : Parcelable
