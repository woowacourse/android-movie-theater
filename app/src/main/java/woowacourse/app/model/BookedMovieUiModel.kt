package woowacourse.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.app.model.movie.MovieUiModel
import java.time.LocalDateTime

@Parcelize
data class BookedMovieUiModel(
    val movie: MovieUiModel,
    val theaterId: Long,
    val ticketCount: Int,
    val bookedDateTime: LocalDateTime,
) : Parcelable
