package woowacourse.app.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.app.presentation.model.movie.MovieUiModel
import java.time.LocalDateTime

@Parcelize
data class BookedMovieUiModel(
    val movie: MovieUiModel,
    val theaterId: Long,
    val ticketCount: Int,
    val bookedDateTime: LocalDateTime,
) : Parcelable
