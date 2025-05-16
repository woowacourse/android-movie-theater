package woowacourse.movie.provider

import android.content.Context
import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.data.dummy.DummyCinema
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.repository.mapper.toEntity

object RepositoryProvider {
    fun ticketRepository(context: Context): TicketRepository {
        val cinemaDao = MovieTheaterDatabase.db(context.applicationContext).cinemaDao()
        cinemaDao.save(
            DummyCinema.dummyCinemas.map { it.toEntity() },
        )
        return TicketRepository(
            ticketDao(context.applicationContext),
        )
    }

    fun settingRepository(context: Context): SettingRepository =
        SettingRepository(
            context.applicationContext.getSharedPreferences("setting", Context.MODE_PRIVATE),
        )

    fun ticketDao(context: Context) = MovieTheaterDatabase.db(context.applicationContext).ticketDao()
}
