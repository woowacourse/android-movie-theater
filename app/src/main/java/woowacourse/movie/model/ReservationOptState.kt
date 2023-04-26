package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationOptState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val countState: CountState
) : Parcelable
