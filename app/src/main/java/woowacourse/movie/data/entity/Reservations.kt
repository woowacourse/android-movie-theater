package woowacourse.movie.data.entity

import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.ui.model.MovieTicketModel

object Reservations {
    private val items = mutableListOf<MovieTicketModel>()
    private var restored = false

    fun restore(repository: ReservationRepository) {
        val tickets = repository.getReservations()
        items.addAll(tickets)
        restored = true
    }

    fun addItem(item: MovieTicketModel) {
        if (restored) items.add(item)
    }

    fun getAll(): List<MovieTicketModel> = items

    fun getSize(): Int = items.size
}
