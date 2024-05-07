package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.TicketDatabase
import woowacourse.movie.data.TicketRepositoryImpl

class MovieTheaterApplication : Application() {
    val ticketDatabase by lazy { TicketDatabase.getInstance(this) }
    val ticketRepository by lazy { TicketRepositoryImpl(ticketDatabase.ticketDao()) }
}
