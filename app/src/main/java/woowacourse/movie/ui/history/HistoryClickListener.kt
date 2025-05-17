package woowacourse.movie.ui.history

import woowacourse.movie.data.BookedTicketEntity

fun interface HistoryClickListener {
    fun onClick(bookedTicketEntity: BookedTicketEntity)
}
