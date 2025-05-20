package woowacourse.movie

import android.app.Application
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
        val database: TicketDatabase = TicketDatabase.getDataBase(this)
        val dao: TicketDao = database.ticketDao()
        val repository: TicketRepository = TicketRepositoryImpl(dao)
        TicketProvider.initTicketRepository(repository)
    }
}
