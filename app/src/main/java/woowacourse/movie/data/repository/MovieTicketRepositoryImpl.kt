package woowacourse.movie.data.repository

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import woowacourse.movie.data.database.MovieTicketDatabase
import woowacourse.movie.data.utils.Constants.DATABASE_NAME
import woowacourse.movie.data.utils.Constants.INVALID_MOVIE_TICKET_ID_MESSAGE
import woowacourse.movie.data.utils.GsonUtil
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
    private val gson: Gson = GsonUtil.gson
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
        val newTicket = MovieTicket(
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
            ticketId = movieTicketDao.insertMovieTicket(newTicket)
            latch.countDown()
        }
        executor.execute(task)
        latch.await()

        return newTicket.copy(id = ticketId)
    }

    override fun getMovieTicket(movieTicketId: Long): MovieTicket {
        var ticket: MovieTicket? = null
        val latch = CountDownLatch(1)
        val task = Runnable {
            ticket = movieTicketDao.getMovieTicket(movieTicketId)
            latch.countDown()
        }
        executor.execute(task)
        latch.await()
        return ticket ?: throw IllegalStateException(INVALID_MOVIE_TICKET_ID_MESSAGE)
    }

    fun movieTicketToJson(movieTicket: MovieTicket): String {
        return gson.toJson(movieTicket)
    }

    fun jsonToMovieTicket(json: String): MovieTicket {
        return gson.fromJson(json, MovieTicket::class.java)
    }
}
