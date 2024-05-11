package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.model.ReservationEntity

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(reservationEntity: ReservationEntity): Long

    @Query("SELECT * FROM reservationEntity WHERE reservationId = :reservationId ")
    fun findReservation(reservationId: Long): ReservationEntity

    @Query("SELECT * from reservationEntity")
    fun getReservations(): List<ReservationEntity>

    @Query("DELETE FROM reservationEntity")
    fun deleteAllReservations()
}
