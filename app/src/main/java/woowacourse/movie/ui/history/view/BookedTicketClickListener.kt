package woowacourse.movie.ui.history.view

import woowacourse.movie.domain.model.theater.BookedTicket

fun interface BookedTicketClickListener {
    fun onClick(bookedTicket: BookedTicket)
}
