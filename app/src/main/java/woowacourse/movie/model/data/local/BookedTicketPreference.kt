package woowacourse.movie.model.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlinx.datetime.serializers.LocalDateTimeComponentSerializer
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import woowacourse.movie.domain.model.tools.Ticket
import woowacourse.movie.model.data.local.SerializableTicketModel.Companion.toSerializable
import woowacourse.movie.model.data.local.SerializableTicketModel.Companion.toUiModel
import woowacourse.movie.model.data.storage.BookedTicketStorage
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.TicketModel
import java.io.Serializable
import kotlinx.serialization.Serializable as kotlinxSerializable

class BookedTicketPreference(private val context: Context) : BookedTicketStorage {
    private val value by lazy {
        context.getSharedPreferences(BOOKED_TICKETS, MODE_PRIVATE)
    }

    override fun getBookedTickets(): List<TicketModel> {
        return SerializableDAO.get<List<SerializableTicketModel>>(value, TICKETS)
            ?.map { it.toUiModel() } ?: emptyList()
    }

    override fun addBookedTicket(ticket: Ticket) {
        SerializableDAO.addToList<SerializableTicketModel>(
            ticket.toPresentation(context).toSerializable(),
            value,
            TICKETS
        )
    }

    companion object {
        private const val BOOKED_TICKETS = "BookedTickets"

        private val TICKETS = "Tickets"
    }
}

@kotlinxSerializable
private data class SerializableTicketModel(
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
