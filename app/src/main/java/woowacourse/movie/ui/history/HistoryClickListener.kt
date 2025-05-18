package woowacourse.movie.ui.history

import woowacourse.movie.domain.model.BookedTicket

fun interface HistoryClickListener {
    fun onClick(bookedTicket: BookedTicket)
}
