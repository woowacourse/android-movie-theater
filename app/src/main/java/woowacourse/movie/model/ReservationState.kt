package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationState(
    val theaterName: String,
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val countState: CountState
) : Parcelable
