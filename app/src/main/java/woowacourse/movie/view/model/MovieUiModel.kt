package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
    override val name: String,
    val poster: Int,
    val startDate: String,
    val endDate: String,
    val runningTime: Int,
) : MovieListItem(ItemViewType.MOVIE_ITEM),
    Parcelable
