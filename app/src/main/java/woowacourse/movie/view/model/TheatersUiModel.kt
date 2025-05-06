package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheatersUiModel(
    val value: List<TheaterUiModel>,
) : Parcelable
