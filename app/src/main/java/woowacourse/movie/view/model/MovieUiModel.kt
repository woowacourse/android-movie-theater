package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class MovieUiModel(
    override val name: String,
    val poster: Int,
    val startDate: String,
    val endDate: String,
    val runningTime: Int,
) : MovieListItem(ItemViewType.MOVIE_ITEM),
    Parcelable

fun MovieUiModel.toDomainModel(): Movie =
    Movie(
        title = this.name,
        poster = this.poster,
        startDate = this.startDate.toLocalDate(),
        endDate = this.endDate.toLocalDate(),
        runningTime = this.runningTime,
    )

fun String.toLocalDate(pattern: String = "yyyy.MM.dd"): LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
