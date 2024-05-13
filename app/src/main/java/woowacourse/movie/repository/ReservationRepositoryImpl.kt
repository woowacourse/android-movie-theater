package woowacourse.movie.repository

import android.content.Context
import android.util.Log
import woowacourse.movie.database.ReservationData
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.model.Seat
import woowacourse.movie.model.Ticket

class ReservationRepositoryImpl(context: Context) : ReservationRepository {
    private val reservationDao = ReservationDatabase.getInstance(context).reservationDao()

    override fun loadReservationList(): List<ReservationData> {
        return reservationDao.getAll()
    }

    override fun saveReservationData(ticket: Ticket): Long {
        val reservationData = makeReservationDataWithTicket(ticket)
        return reservationDao.insert(reservationData)
    }

    private fun makeReservationDataWithTicket(ticket: Ticket): ReservationData {
        return ReservationData(
            theaterId = ticket.theaterId,
            movieTitle = ticket.movieTitle,
            screenDate = ticket.screeningDateTime.split(' ')[0],
            screenTime = ticket.screeningDateTime.split(' ')[1],
            selectedSeats = ticket.selectedSeatsToString(),
            totalPrice = ticket.totalPriceToString(),
        )
    }

    override fun findReservationData(ticketId: Long): ReservationData? {
        return reservationDao.selectWithId(ticketId)
    }

    override fun findTicket(ticketId: Long): Ticket? {
        var reservationData: ReservationData? = null
        val t =
            Thread {
                reservationData = findReservationData(ticketId)
            }
        t.start()
        t.join()
        Log.d("repo crong", "$ticketId")
        Log.d("repo crong", "$reservationData")
        return reservationData?.let {
            Ticket(
                movieTitle = it.movieTitle,
                screeningDateTime = "${reservationData!!.screenDate} ${reservationData!!.screenTime}",
                selectedSeats = reservationData!!.selectedSeats.toSeatList(),
                theaterId = reservationData!!.theaterId,
            )
        }
    }

    private fun String.toSeatList(): List<Seat> {
        val stringSeatList = this.split(", ")
        return stringSeatList.map {
            Seat(
                it[0] - 'A',
                it[1].digitToInt() - 1,
            )
        }
    }
}
