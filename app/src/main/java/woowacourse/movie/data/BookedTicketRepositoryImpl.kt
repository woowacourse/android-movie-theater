package woowacourse.movie.data

import kotlin.concurrent.thread
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.BookedTicketRepository
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seats

class BookedTicketRepositoryImpl(
    private val database: BookedTicketDatabase
) : BookedTicketRepository {
    private val dao: BookedTicketDao by lazy { database.bookedTicketDao() }
    override fun insert(bookedTicket: BookedTicket) {
        thread {
            val bookedTicketEntity = bookedTicket.toBookedTicketEntity()
            dao.insert(bookedTicketEntity)
        }
    }

    override fun fetchById(id: Long, onTicketLoaded: (BookedTicket) -> Unit) {
        thread {
            val entity: BookedTicketEntity = dao.findBookedTicketEntityById(id)
            onTicketLoaded(entity.toBookedTicket())
        }
    }

    override fun fetchAll(onTicketsLoaded: (List<BookedTicket>) -> Unit) {
        thread {
            val entities: List<BookedTicketEntity> = dao.findAll()
            val bookedTickets = entities.map { it.toBookedTicket() }
            onTicketsLoaded(bookedTickets)
        }
    }
}

fun BookedTicketEntity.toBookedTicket(): BookedTicket {
    return BookedTicket(
        id = this.id,
        theaterName = this.theaterName,
        movieTitle = this.movieTitle,
        movieSchedule =
            MovieSchedule(
                this.screeningDateTime,
                Seats(this.selectSeats.toMutableSet()),
            ),
        headcount = Headcount(headcount),
    )
}

fun BookedTicket.toBookedTicketEntity(): BookedTicketEntity {
    return BookedTicketEntity(
        id = this.id,
        theaterName = theaterName,
        movieTitle = movieTitle,
        screeningDateTime = movieSchedule.screeningDateTime,
        selectSeats = movieSchedule.seats.reservingSeats,
        headcount = headcount.count,
    )
}

