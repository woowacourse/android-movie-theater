package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.dummy.DummyCinema
import woowacourse.movie.repository.mapper.toEntity
import kotlin.concurrent.thread

object DatabaseProvider {
    fun ticketDao(context: Context): TicketDao {
        initData(context.applicationContext)
        return db(context.applicationContext).ticketDao()
    }

    private fun initData(context: Context) {
        val cinemaDao = db(context.applicationContext).cinemaDao()
        thread {
            cinemaDao.save(
                DummyCinema.dummyCinemas.map { it.toEntity() },
            )
        }.join()
    }

    private fun db(context: Context): MovieTheaterDatabase {
        return MovieTheaterDatabase.db(context.applicationContext)
    }
}
