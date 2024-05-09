package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.ReservationEntity
import woowacourse.movie.data.entity.SeatEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveReservation(reservation: ReservationEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveMovieSeats(seats: List<SeatEntity>)

    @Query(
        """
        SELECT * FROM 
        (SELECT * FROM reservation WHERE id = :id) as res
        JOIN movie_seats AS seat
        ON res.id = seat.reservationId
        ORDER BY seat.rowNumber, seat.columnNumber
    """
    )
    fun loadReservationWithSeats(id: Long): Map<ReservationEntity, List<SeatEntity>>

    @Query("SELECT * FROM reservation where id = :id")
    fun loadReservation(id: Long): ReservationEntity

    @Query(
        """
        SELECT * FROM reservation
        JOIN movie_seats AS seat
        ON reservation.id = seat.reservationId
        ORDER BY seat.rowNumber, seat.columnNumber
    """
    )
    fun loadAllReservations(): Map<ReservationEntity, List<SeatEntity>>

    @Query("DELETE FROM reservation WHERE id = :id")
    fun deleteReservation(id: Long)

    @Query("DELETE FROM reservation")
    fun deleteAllReservations()
}