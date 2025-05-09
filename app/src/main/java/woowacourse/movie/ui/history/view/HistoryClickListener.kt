package woowacourse.movie.ui.history.view

import woowacourse.movie.data.BookedTicketEntity

fun interface HistoryClickListener {
    fun onClick(bookedTicketEntity: BookedTicketEntity)
}
