package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class SelectReservationState(
    val theater: TheaterState,
    val movie: MovieState,
    val selectDateTime: LocalDateTime,
    val selectCount: CountState
) : Parcelable
