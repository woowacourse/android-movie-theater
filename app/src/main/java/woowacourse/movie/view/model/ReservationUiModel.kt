package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationUiModel(
    val title: String,
    val screeningDateTime: LocalDateTime,
    val count: Int,
    val seats: List<String>,
    val price: Int,
    val theaterName: String
) : Parcelable
