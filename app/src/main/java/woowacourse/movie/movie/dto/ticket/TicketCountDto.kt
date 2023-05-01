package woowacourse.movie.movie.dto.ticket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketCountDto(
    val numberOfPeople: Int = MIN_BOOKER_NUMBER,
) : Parcelable {
    companion object {
        const val MIN_BOOKER_NUMBER = 1
    }
}
