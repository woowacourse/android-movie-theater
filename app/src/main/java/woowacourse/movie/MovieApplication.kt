package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.setting.SettingStorageManagerImpl
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepository

class MovieApplication : Application() {
    private val database by lazy { TicketDatabase.database(this) }
    val repository by lazy { TicketRepository(database.ticketDao()) }
    val settingStorageManager by lazy { SettingStorageManagerImpl(applicationContext) }
}
