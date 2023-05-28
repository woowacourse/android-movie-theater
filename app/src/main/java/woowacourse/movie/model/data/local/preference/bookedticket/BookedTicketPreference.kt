package woowacourse.movie.model.data.local.preference.bookedticket

import android.content.Context
import android.content.Context.MODE_PRIVATE
import woowacourse.movie.domain.model.tools.Ticket
import woowacourse.movie.model.data.local.SerializableDAO
import woowacourse.movie.model.data.local.preference.bookedticket.SerializableTicketModel.Companion.toSerializable
import woowacourse.movie.model.data.local.preference.bookedticket.SerializableTicketModel.Companion.toUiModel
import woowacourse.movie.model.data.storage.BookedTicketStorage
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.TicketModel

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
