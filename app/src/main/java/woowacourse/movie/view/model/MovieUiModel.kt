package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Movie

@Parcelize
data class MovieUiModel(
    override val name: String,
    val poster: Int,
    val date: MovieDateUiModel,
    val runningTime: Int,
) : MovieListItem(ItemViewType.MOVIE_ITEM),
    Parcelable

fun Movie.toPresentation(): MovieUiModel = MovieUiModel(title, poster, date.toPresentation(), runningTime)

fun MovieUiModel.toDomain(): Movie = Movie(name, poster, date.toDomain(), runningTime)
