package woowacourse.movie.feature.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
    override val id: Long = 0L,
    val title: String = "",
    val startDate: MovieDateUiModel = MovieDateUiModel(),
    val endDate: MovieDateUiModel = MovieDateUiModel(),
    val runningTime: Int = 0,
    @DrawableRes val poster: Int = 0,
) : ContentUiModel(id),
    Parcelable
