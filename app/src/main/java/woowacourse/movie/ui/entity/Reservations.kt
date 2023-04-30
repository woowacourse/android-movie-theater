package woowacourse.movie.ui.entity

import woowacourse.movie.ui.model.MovieTicketModel

object Reservations {
    private val items = mutableListOf<MovieTicketModel>()

    fun addItem(item: MovieTicketModel) {
        items.add(item)
    }

    fun getAll(): List<MovieTicketModel> = items

    fun getSize(): Int = items.size
}
