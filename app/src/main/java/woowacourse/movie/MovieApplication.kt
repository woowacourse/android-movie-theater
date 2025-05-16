package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.setting.SettingRepositoryImpl
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepositoryImpl

class MovieApplication : Application() {
    private val database by lazy { TicketDatabase.database(this) }
    val repository by lazy { TicketRepositoryImpl(database.ticketDao()) }
    val settingStorageManager by lazy { SettingRepositoryImpl(applicationContext) }
}
