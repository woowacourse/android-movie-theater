package woowacourse.movie.model.data.local.preference.bookedticket

import kotlinx.datetime.serializers.LocalDateTimeComponentSerializer
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import woowacourse.movie.presentation.model.TicketModel
import java.io.Serializable
import kotlinx.serialization.Serializable as kotlinxSerializable

@kotlinxSerializable
data class SerializableTicketModel(
    val ticketId: Int,
    val movieId: Long,
    @kotlinxSerializable(with = LocalDateTimeComponentSerializer::class)
    val bookedDateTime: kotlinx.datetime.LocalDateTime,
    val count: Int,
    val paymentMoney: Int,
    val seats: List<String>,
    val theater: String
) : Serializable {
    companion object {
        fun TicketModel.toSerializable(): SerializableTicketModel =
            SerializableTicketModel(
                ticketId,
                movieId,
                bookedDateTime.toKotlinLocalDateTime(),
                count,
                paymentMoney,
                seats,
                theater
            )

        fun SerializableTicketModel.toUiModel(): TicketModel =
            TicketModel.fromSerializableTicketModel(
                ticketId,
                movieId,
                bookedDateTime.toJavaLocalDateTime(),
                count,
                paymentMoney,
                seats,
                theater
            )
    }
}
