package woowacourse.movie.presentation.model

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.data.local.TicketIssuePreference
import java.time.LocalDateTime

@Parcelize
data class TicketModel private constructor(
    val ticketId: Int,
    val movieId: Long,
    val bookedDateTime: LocalDateTime,
    val count: Int,
    val paymentMoney: Int,
    val seats: List<String>,
    val theater: String
) : Parcelable {
    companion object {
        fun of(
            context: Context,
            movieId: Long,
            bookedDateTime: LocalDateTime,
            count: Int,
            paymentMoney: Int,
            seats: List<String>,
            theater: String
        ): TicketModel =
            TicketModel(
                TicketIssuePreference(context).issueTicketId(),
                movieId,
                bookedDateTime,
                count,
                paymentMoney,
                seats,
                theater
            )
    }
}
