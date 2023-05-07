package woowacourse.movie.model.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlinx.datetime.serializers.LocalDateTimeComponentSerializer
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import woowacourse.movie.domain.model.tools.Ticket
import woowacourse.movie.model.data.local.SerializableTicketModel.Companion.toSerializableTicketModel
import woowacourse.movie.model.data.local.SerializableTicketModel.Companion.toTicketModel
import woowacourse.movie.model.data.storage.BookedTicketStorage
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.TicketModel
import java.io.Serializable

class BookedTicketPreference(private val context: Context) : BookedTicketStorage {
    private val value by lazy {
        context.getSharedPreferences(BOOKED_TICKETS, MODE_PRIVATE)
    }

    override fun getBookedTickets(): List<TicketModel> {
        val json: String = value.getString(TICKETS, null) ?: return emptyList()
        return jsonConvertToTickets(json).map { it.toTicketModel() }
    }

    override fun addBookedTicket(ticket: Ticket) {
        val json: String =
            value.getString(TICKETS, null)
                ?: Json.encodeToString(emptyList<SerializableTicketModel>())
        val oldTickets: List<SerializableTicketModel> = jsonConvertToTickets(json)
        val newTickets: List<SerializableTicketModel> =
            oldTickets + ticket.toPresentation(context).toSerializableTicketModel()
        value.edit().putString(TICKETS, ticketsConvertToJson(newTickets)).apply()
    }

    private fun jsonConvertToTickets(json: String): List<SerializableTicketModel> =
        Json.decodeFromString<List<SerializableTicketModel>>(json)

    private fun ticketsConvertToJson(tickets: List<SerializableTicketModel>): String =
        Json.encodeToString(tickets)

    companion object {
        private const val BOOKED_TICKETS = "BookedTickets"

        private val TICKETS = "Tickets"
    }
}

@kotlinx.serialization.Serializable
private data class SerializableTicketModel(
    val ticketId: Int,
    val movieId: Long,
    @kotlinx.serialization.Serializable(with = LocalDateTimeComponentSerializer::class)
    val bookedDateTime: kotlinx.datetime.LocalDateTime,
    val count: Int,
    val paymentMoney: Int,
    val seats: List<String>,
    val theater: String
) : Serializable {
    companion object {
        fun TicketModel.toSerializableTicketModel(): SerializableTicketModel =
            SerializableTicketModel(
                ticketId,
                movieId,
                bookedDateTime.toKotlinLocalDateTime(),
                count,
                paymentMoney,
                seats,
                theater
            )

        fun SerializableTicketModel.toTicketModel(): TicketModel =
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
