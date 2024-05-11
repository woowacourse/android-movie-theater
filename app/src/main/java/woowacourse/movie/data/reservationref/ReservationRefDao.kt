package woowacourse.movie.data.reservationref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationRefDao {
    @Insert
    fun insert(reservationRefDto: ReservationRefDto): Long

    @Query("select * from reservationRefs where id == :id")
    fun findById(id: Long): ReservationRefDto?

    @Query("select * from reservationRefs")
    fun findAll(): List<ReservationRefDto>
}
