package woowacourse.movie.view.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

sealed class MovieListModel {

    data class MovieAdModel(
        @DrawableRes val banner: Int,
        val url: String
    ) : MovieListModel()

    @Parcelize
    data class MovieUiModel(
        val title: String,
        val screeningDates: List<LocalDate>,
        val runningTime: Int,
        val posterResourceId: Int,
        val summary: String
    ) : Parcelable, MovieListModel()
}
