package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationOptions(
    val theaterName: String,
    val title: String,
    val screeningDateTime: LocalDateTime,
    val peopleCount: Int
) : Parcelable
