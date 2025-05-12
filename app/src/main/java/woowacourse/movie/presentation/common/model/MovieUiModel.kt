package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.R
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.domain.model.movie.RunningTime

@Parcelize
data class MovieUiModel(
    val id: Int,
    val title: String,
    @DrawableRes val poster: Int,
    val screeningPeriod: ScreeningPeriodUiModel,
    val runningTime: Int,
) : Parcelable

fun Movie.toUiModel(): MovieUiModel = MovieUiModel(id, title, mappingPoster(id), screeningPeriod.toUiModel(), runningTime.minute)

fun MovieUiModel.toDomain(): Movie = Movie(id, title, screeningPeriod.toDomain(), RunningTime(runningTime))

private fun mappingPoster(movieId: Int) = when (movieId) {
    else -> R.drawable.harrypotter
}
