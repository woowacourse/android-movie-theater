package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.dummy.DummyCinema
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.repository.mapper.toEntity

object RepositoryProvider {
    fun ticketRepository(context: Context): TicketRepository {
        return TicketRepository(
            ticketDao(context.applicationContext),
        )
    }

    fun settingRepository(context: Context): SettingRepository =
        SettingRepository(
            context.applicationContext.getSharedPreferences("setting", Context.MODE_PRIVATE),
        )

    fun ticketDao(context: Context): TicketDao {
        initData(context.applicationContext)
        return db(context.applicationContext).ticketDao()
    }

    private fun initData(context: Context) {
        val cinemaDao = db(context.applicationContext).cinemaDao()
        cinemaDao.save(
            DummyCinema.dummyCinemas.map { it.toEntity() },
        )
    }

    private fun db(context: Context): MovieTheaterDatabase {
        return MovieTheaterDatabase.db(context.applicationContext)
    }
}
