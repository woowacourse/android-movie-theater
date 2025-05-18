package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity

@Dao
interface ReservationDao {
    @Transaction
    @Query("SELECT * FROM reservation")
    fun getAllMovieTickets(): List<MovieTicketEntity>

    @Insert
    fun insertReservation(reservation: ReservationInfoEntity): Long

    @Insert
    fun insertSeats(seats: List<SeatEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTheater(theater: TheaterEntity)

    @Transaction
    fun insertMovieTicketEntity(ticket: MovieTicketEntity): Long {
        insertMovie(ticket.movie)
        insertTheater(ticket.theater)

        val reservationId: Long = insertReservation(ticket.reservationInfoEntity)

        val updatedSeats =
            ticket.seats.map {
                it.copy(reservationId = reservationId)
            }
        insertSeats(updatedSeats)
        return reservationId
    }

    @Transaction
    @Query("SELECT * FROM reservation WHERE id = :reservationId")
    fun getMovieTicketByReservationId(reservationId: Long): MovieTicketEntity?
}
