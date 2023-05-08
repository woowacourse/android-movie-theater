package woowacourse.movie.data.entity

import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.ui.model.MovieTicketModel

object Reservations {
    private val items = mutableListOf<MovieTicketModel>()

    fun restore(repository: ReservationRepository) {
        val tickets = repository.getReservations()
        items.addAll(tickets)
    }

    fun addItem(item: MovieTicketModel) {
        items.add(item)
    }

    fun getAll(): List<MovieTicketModel> = items

    fun getSize(): Int = items.size
}
