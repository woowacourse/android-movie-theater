package woowacourse.movie.view.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

sealed class MovieListModel {

    data class MovieAdModel(
        @DrawableRes val banner: Int,
        val url: String,
    ) : MovieListModel()

    @Parcelize
    data class MovieUiModel(
        val title: String,
        val screeningDateTimes: Map<LocalDate, List<LocalTime>>,
        val runningTime: Int,
        val posterResourceId: Int,
        val summary: String,
    ) : Parcelable, MovieListModel()
}
