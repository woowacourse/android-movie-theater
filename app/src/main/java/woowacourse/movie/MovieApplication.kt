package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.setting.SettingRepositoryImpl
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepositoryImpl

class MovieApplication : Application() {
    private val ticketDatabase by lazy { TicketDatabase.database(this) }
    val ticketRepository by lazy { TicketRepositoryImpl(ticketDatabase.ticketDao()) }
    val settingRepository by lazy { SettingRepositoryImpl(applicationContext) }
}
