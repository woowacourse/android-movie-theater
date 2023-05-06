package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReservationModel(
    val cinemaModel: CinemaModel,
    val bookedDateTime: LocalDateTime,
    val count: Int,
) : Parcelable
