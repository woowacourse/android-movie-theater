package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.MovieReservationEntity

@Dao
interface MovieReservationDao {
    @Query("SELECT * FROM movie_reservation")
    fun getAll(): List<MovieReservationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movieReservationEntities: MovieReservationEntity): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieReservationEntity: MovieReservationEntity): Long

    @Delete
    fun delete(movieReservationEntity: MovieReservationEntity)

    @Query("SELECT * FROM movie_reservation WHERE id==:reservationId")
    fun getMovieReservationById(reservationId: Long): MovieReservationEntity
}
