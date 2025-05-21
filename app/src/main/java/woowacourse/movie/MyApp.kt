package woowacourse.movie

import android.app.Application
import android.content.Context
import woowacourse.movie.data.NotificationRepository
import woowacourse.movie.data.NotificationRepositoryImpl
import woowacourse.movie.data.TicketDao
import woowacourse.movie.data.TicketDatabase
import woowacourse.movie.data.TicketRepositoryImpl
import woowacourse.movie.domain.TicketRepository

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initProvider()
    }

    private fun initProvider() {
        initTicketRepository()
        initNotificationRepository()
    }

    private fun initTicketRepository() {
        val database: TicketDatabase = TicketDatabase.getDataBase(this)
        val dao: TicketDao = database.ticketDao()
        val repository: TicketRepository = TicketRepositoryImpl(dao)
        TicketProvider.initTicketRepository(repository)
    }

    private fun initNotificationRepository() {
        val preferences = this.getSharedPreferences("setting_preferences", Context.MODE_PRIVATE)
        val repository: NotificationRepository = NotificationRepositoryImpl(preferences)
        TicketProvider.initNotificationRepository(repository)
    }
}
