package woowacourse.movie.ui.reservation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationDetail(
    val title: String,
    val theater: String,
    val screeningDateTime: LocalDateTime,
    val count: Int,
) : Parcelable
