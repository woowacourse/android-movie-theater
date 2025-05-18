package woowacourse.movie.repository

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.mapper.toEntity
import woowacourse.movie.repository.mapper.toTicket
import kotlin.concurrent.thread

class TicketRepository(
    val ticketDao: TicketDao,
) {
    val handler = Handler(Looper.getMainLooper())

    fun findAll(callback: (Result<List<Ticket>>) -> Unit) {
        thread {
            runCatching {
                ticketDao.findAll().map {
                    it.toTicket()
                }
            }.onSuccess {
                handler.post {
                    callback(Result.success(it))
                }
            }.onFailure {
                handler.post {
                    callback(Result.failure(it))
                }
            }
        }
    }

    fun save(
        ticket: Ticket,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            runCatching {
                ticketDao.save(
                    ticket.toEntity(),
                    ticket.seats.map {
                        SeatEntity(
                            row = it.row,
                            column = it.column,
                        )
                    },
                )
            }.onSuccess {
                handler.post {
                    callback(Result.success(Unit))
                }
            }.onFailure {
                handler.post {
                    callback(Result.failure(it))
                }
            }
        }
    }
}
