package woowacourse.movie.view.movies.adapter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.movies.adapter.TheaterAdapter

@Parcelize
sealed class TheaterRvItem(val viewType: TheaterAdapter.ViewType) : Parcelable {
    data class TheaterItem(
        val name: String,
        val movieId: Int,
        val bookingAbleTimeCount: Int,
    ) : TheaterRvItem(TheaterAdapter.ViewType.VIEW_TYPE_THEATER)
}

fun Theater.toItem(movieId: Int): TheaterRvItem.TheaterItem {
    val count = screeningTimeCount(movieId)
    return TheaterRvItem.TheaterItem(
        name = name,
        movieId = movieId,
        bookingAbleTimeCount = count,
    )
}
