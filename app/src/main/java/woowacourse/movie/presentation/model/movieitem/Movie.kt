package woowacourse.movie.presentation.model.movieitem

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class Movie(
    val id: Int = 0,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val introduce: String,
    @DrawableRes val thumbnail: Int,
) : ListItem, Parcelable {
    @IgnoredOnParcel
    val formattedStartDate: String =
        startDate.format(DateTimeFormatter.ofPattern(MOVIE_DATE_PATTERN))

    @IgnoredOnParcel
    val formattedEndDate: String =
        endDate.format(DateTimeFormatter.ofPattern(MOVIE_DATE_PATTERN))

    companion object {
        internal const val MOVIE_DATE_PATTERN = "yyyy.MM.dd"
    }

    override fun isAd(): Boolean = false
}
