package woowacourse.movie.view.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Movie(
    override val name: String,
    val poster: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
) : MainItem(ItemViewType.MOVIE_ITEM),
    Parcelable
