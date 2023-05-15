package woowacourse.movie.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.domain.model.Money
import com.example.domain.model.Ticket
import com.example.domain.model.Tickets
import com.example.domain.model.seat.SeatPosition
import com.example.domain.repository.dataSource.movieDataSources
import com.example.domain.repository.dataSource.theaterDataSources
import java.time.LocalDateTime

class ReservationTicketsDao(
    context: Context
) : SQLiteOpenHelper(context, db_name, null, db_version) {

    private val tables =
        listOf<TicketsDataBaseTableContract>(
            ReservationTicketsContract,
            ReservationSeatPositionContract
        )

    override fun onCreate(db: SQLiteDatabase?) {
        tables.forEach {
            db?.execSQL(it.createSQL())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        tables.forEach {
            db?.execSQL("DROP TABLE IF EXISTS ${it.TABLE_NAME}")
        }
        onCreate(db)
    }

    fun insertTickets(tickets: Tickets) {
        val values = ContentValues().apply {
            put(ReservationTicketsContract.TABLE_COLUMN_THEATER_ID, tickets.theater.theaterId)
            put(ReservationTicketsContract.TABLE_COLUMN_MOVIE_ID, tickets.movie.movieId)
            put(ReservationTicketsContract.TABLE_COLUMN_YEAR, tickets.dateTime.year)
            put(ReservationTicketsContract.TABLE_COLUMN_MONTH, tickets.dateTime.monthValue)
            put(ReservationTicketsContract.TABLE_COLUMN_DAY, tickets.dateTime.dayOfMonth)
            put(ReservationTicketsContract.TABLE_COLUMN_HOUR, tickets.dateTime.hour)
            put(ReservationTicketsContract.TABLE_COLUMN_MINUTES, tickets.dateTime.minute)
            put(
                ReservationTicketsContract.TABLE_COLUMN_ALL_DISCOUNTED_MONEY,
                tickets.getTotalDiscountedMoney().value
            )
        }
        val reservationId =
            writableDatabase.insert(ReservationTicketsContract.TABLE_NAME, null, values)
        insertSeatPosition(reservationId, tickets)
    }

    private fun insertSeatPosition(reservationId: Long, tickets: Tickets) {
        tickets.tickets.forEach { ticket ->
            val values = ContentValues().apply {
                put(ReservationSeatPositionContract.TABLE_COLUMN_RESERVATION_ID, reservationId)
                put(
                    ReservationSeatPositionContract.TABLE_COLUMN_SEAT_POSITION_ROW,
                    ticket.position.row.ordinal
                )
                put(
                    ReservationSeatPositionContract.TABLE_COLUMN_SEAT_POSITION_COLUMN,
                    ticket.position.column.ordinal
                )
                put(
                    ReservationSeatPositionContract.TABLE_COLUMN_DISCOUNTED_MONEY,
                    ticket.discountedMoney.value
                )
            }
            writableDatabase.insert(ReservationSeatPositionContract.TABLE_NAME, null, values)
        }
    }

    fun selectAllTickets(): List<Tickets> {
        val ticketDtos = mutableListOf<TicketDto>()
        val selectSql =
            "SELECT a.*, b.* FROM ${ReservationTicketsContract.TABLE_NAME} a, ${ReservationSeatPositionContract.TABLE_NAME} b " +
                "on a.${ReservationTicketsContract.TABLE_COLUMN_RESERVATION_ID} = b.${ReservationSeatPositionContract.TABLE_COLUMN_RESERVATION_ID};"
        val cursor = readableDatabase.rawQuery(selectSql, null)
        with(cursor) {
            while (cursor.moveToNext()) {
                val reservationId =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_RESERVATION_ID))
                val theaterId =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_THEATER_ID))
                val movieId =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_MOVIE_ID))
                val year =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_YEAR))
                val month =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_MONTH))
                val day =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_DAY))
                val hour =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_HOUR))
                val minutes =
                    getInt(cursor.getColumnIndexOrThrow(ReservationTicketsContract.TABLE_COLUMN_MINUTES))
                val row =
                    getInt(cursor.getColumnIndexOrThrow(ReservationSeatPositionContract.TABLE_COLUMN_SEAT_POSITION_ROW))
                val column =
                    getInt(cursor.getColumnIndexOrThrow(ReservationSeatPositionContract.TABLE_COLUMN_SEAT_POSITION_COLUMN))
                val discountedMoney =
                    getInt(cursor.getColumnIndexOrThrow(ReservationSeatPositionContract.TABLE_COLUMN_DISCOUNTED_MONEY))

                ticketDtos.add(
                    TicketDto(
                        reservationId,
                        theaterId,
                        movieId,
                        year,
                        month,
                        day,
                        hour,
                        minutes,
                        row,
                        column,
                        discountedMoney
                    )
                )
            }
        }
        cursor.close()
        return ticketDtos.groupBy { it.reservationId }
            .map { Tickets(it.value.map { it.toTicket() }) }
    }

    private data class TicketDto(
        val reservationId: Int,
        val theaterId: Int,
        val movieId: Int,
        val year: Int,
        val month: Int,
        val day: Int,
        val hour: Int,
        val minutes: Int,
        val row: Int,
        val column: Int,
        val discountedMoney: Int
    ) {
        fun toTicket(): Ticket {
            return Ticket(
                theaterDataSources.find { it.theaterId == theaterId }
                    ?: throw IllegalStateException(),
                movieDataSources.find { it.movieId == movieId }
                    ?: throw IllegalStateException(),
                LocalDateTime.of(year, month, day, hour, minutes),
                SeatPosition(row + 1, column + 1),
                Money(discountedMoney)
            )
        }
    }

    companion object {
        private const val db_name = "reservation_tickets_db"
        private const val db_version = 1
    }
}
