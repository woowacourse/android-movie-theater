package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.TicketDatabase
import woowacourse.movie.data.TicketRepositoryImpl
import woowacourse.movie.util.SharedPreferencesManager

class MovieTheaterApplication : Application() {
    private val ticketDatabase by lazy { TicketDatabase.getInstance(this) }
    val ticketRepository by lazy { TicketRepositoryImpl(ticketDatabase.ticketDao()) }
    val sharedPreferencesManager by lazy { SharedPreferencesManager(this) }
}
