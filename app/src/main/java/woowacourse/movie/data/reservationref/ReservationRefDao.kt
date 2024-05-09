package woowacourse.movie.data.reservationref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationRefDao {
    @Insert
    fun insert(reservationRef: ReservationRefDto)

    @Query("select * from reservationRefs where id == :id")
    fun findById(id: Long): ReservationRefDto
}
