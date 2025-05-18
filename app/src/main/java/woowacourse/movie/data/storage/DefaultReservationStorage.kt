package woowacourse.movie.data.storage

import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.data.mapper.toEntity
import woowacourse.movie.model.ticket.MovieTicket
import kotlin.concurrent.thread

class DefaultReservationStorage(
    private val database: AppDatabase,
) : ReservationStorage {
    override fun saveMovieTicket(
        movieTicket: MovieTicket,
        onComplete: (reservationId: Long) -> Unit,
    ) {
        thread {
            val reservationId: Long =
                database.reservationDao().insertMovieTicketEntity(movieTicket.toEntity())
            onComplete.invoke(reservationId)
        }
    }

    override fun getMovieTicket(
        reservationId: Long,
        onComplete: (movieTicket: MovieTicket?) -> Unit,
    ) {
        thread {
            val movieTicketEntity =
                database.reservationDao().getMovieTicketByReservationId(reservationId)
            onComplete.invoke(movieTicketEntity?.toDomain())
        }
    }

    override fun getAllMovieTickets(onComplete: (List<MovieTicketEntity>) -> Unit) {
        thread {
            onComplete.invoke(database.reservationDao().getAllMovieTickets())
        }
    }
}
