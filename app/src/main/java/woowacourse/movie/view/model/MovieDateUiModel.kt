package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.MovieDate
import java.time.LocalDate

@Parcelize
data class MovieDateUiModel(
    val startDate: LocalDate,
    val endDate: LocalDate,
) : Parcelable

fun MovieDate.toPresentation() = MovieDateUiModel(startDate, endDate)

fun MovieDateUiModel.toDomain() = MovieDate(startDate, endDate)
