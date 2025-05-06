package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheaterUiModels(
    val value: List<TheaterUiModel>,
) : Parcelable
