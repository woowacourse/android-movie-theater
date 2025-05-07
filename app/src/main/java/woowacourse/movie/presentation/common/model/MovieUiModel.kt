package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.RunningTime

@Parcelize
data class MovieUiModel(
    val id: Int,
    val title: String,
    val poster: PosterUiModel,
    val screeningPeriod: ScreeningPeriodUiModel,
    val runningTime: Int,
) : Parcelable

fun Movie.toUiModel(): MovieUiModel = MovieUiModel(id, title, poster.toUiModel(), screeningPeriod.toUiModel(), runningTime.minute)

fun MovieUiModel.toDomain(): Movie = Movie(id, title, poster.toDomain(), screeningPeriod.toDomain(), RunningTime(runningTime))
