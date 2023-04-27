package woowacourse.movie.model

import android.os.Parcelable
import java.time.LocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeatSelectState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val countState: CountState
) : Parcelable
