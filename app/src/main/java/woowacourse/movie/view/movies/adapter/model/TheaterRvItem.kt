package woowacourse.movie.view.movies.adapter.model

import android.os.Parcelable
import androidx.annotation.LayoutRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.R
import woowacourse.movie.domain.model.theater.Theater

@Parcelize
sealed class TheaterRvItem(val viewType: ViewType) : Parcelable {
    data class TheaterItem(
        val name: String,
        val movieId: Int,
        val bookingAbleTimeCount: Int,
    ) : TheaterRvItem(ViewType.VIEW_TYPE_THEATER)

    enum class ViewType(
        @LayoutRes val layoutRes: Int,
    ) {
        VIEW_TYPE_THEATER(R.layout.theater_item),
    }
}

fun Theater.toItem(movieId: Int): TheaterRvItem.TheaterItem {
    val count = screeningTimeCount(movieId)
    return TheaterRvItem.TheaterItem(
        name = name,
        movieId = movieId,
        bookingAbleTimeCount = count,
    )
}
