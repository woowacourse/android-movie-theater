package woowacourse.movie.data.repository

import android.content.Context
import androidx.room.Room
import woowacourse.movie.data.database.MovieTicketDatabase
import woowacourse.movie.data.mapper.toDomainModel
import woowacourse.movie.data.model.MovieTicketEntity
import woowacourse.movie.data.utils.DataConstants.DATABASE_NAME
import woowacourse.movie.data.utils.DataConstants.INVALID_MOVIE_TICKET_ID_MESSAGE
import woowacourse.movie.domain.model.movieticket.MovieTicket
import woowacourse.movie.domain.repository.MovieTicketRepository
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class MovieTicketRepositoryImpl(context: Context) : MovieTicketRepository {

    private val db = Room.databaseBuilder(
        context,
        MovieTicketDatabase::class.java, DATABASE_NAME
    ).build()

    private val movieTicketDao = db.movieTicketDao()
    private val executor = Executors.newSingleThreadExecutor()

    override fun createMovieTicket(
        theaterName: String,
        movieTitle: String,
        screeningDate: String,
        screeningTime: String,
        reservationCount: Int,
        reservationSeats: String,
        totalPrice: Int
    ): MovieTicket {
        val newTicketEntity = MovieTicketEntity(
            theaterName = theaterName,
            movieTitle = movieTitle,
            screeningDate = screeningDate,
            screeningTime = screeningTime,
            reservationCount = reservationCount,
            reservationSeats = reservationSeats,
            totalPrice = totalPrice
        )

        val latch = CountDownLatch(1)
        var ticketId: Long = 0L

        val task = Runnable {
            ticketId = movieTicketDao.insertMovieTicket(newTicketEntity)
            latch.countDown()
        }
        executor.execute(task)
        latch.await()

        return newTicketEntity.toDomainModel().copy(id = ticketId)
    }

    override fun getMovieTicket(movieTicketId: Long): MovieTicket {
        var ticketEntity: MovieTicketEntity? = null
        val latch = CountDownLatch(1)
        val task = Runnable {
            ticketEntity = movieTicketDao.getMovieTicket(movieTicketId)
            latch.countDown()
        }
        executor.execute(task)
        latch.await()

        return ticketEntity?.toDomainModel() ?: throw IllegalStateException(INVALID_MOVIE_TICKET_ID_MESSAGE)
    }

    override fun findAll(): List<MovieTicket> {
        var ticketEntities: List<MovieTicketEntity> = emptyList()
        val latch = CountDownLatch(1)
        val task = Runnable {
            ticketEntities = movieTicketDao.getAllMovieTickets()
            latch.countDown()
        }
        executor.execute(task)
        latch.await()

        return ticketEntities.map { it.toDomainModel() }
    }
}
