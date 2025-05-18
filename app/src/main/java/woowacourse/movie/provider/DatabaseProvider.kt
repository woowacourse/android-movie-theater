package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.dummy.DummyCinema
import woowacourse.movie.repository.mapper.toEntity
import kotlin.concurrent.thread

class DatabaseProvider(context: Context) {
    val db: MovieTheaterDatabase = MovieTheaterDatabase.db(context.applicationContext)
    val ticketDao: TicketDao = db.ticketDao()

    init {
        val cinemaDao = db.cinemaDao()
        thread {
            cinemaDao.save(
                DummyCinema.dummyCinemas.map { it.toEntity() },
            )
        }.join()
    }
}
