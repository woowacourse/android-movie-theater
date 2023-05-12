package woowacourse.movie.entity

import woowacourse.movie.model.MovieTicketModel

object Reservations {
    private val items = mutableListOf<MovieTicketModel>()

    fun addItem(item: MovieTicketModel) {
        items.add(item)
    }

    fun getAll(): List<MovieTicketModel> = items

    fun getSize(): Int = items.size
}
