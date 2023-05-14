package woowacourse.movie.data.entity

import woowacourse.movie.ui.model.MovieTicketModel

object Reservations {
    private val items = mutableListOf<MovieTicketModel>()
    private var restored = false

    fun restore(tickets: List<MovieTicketModel>) {
        items.addAll(tickets)
        restored = true
    }

    fun addItem(ticket: MovieTicketModel) {
        if (restored) items.add(ticket)
    }

    fun getAll(): List<MovieTicketModel> = items

    fun getSize(): Int = items.size
}
