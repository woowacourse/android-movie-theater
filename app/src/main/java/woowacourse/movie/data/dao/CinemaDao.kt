package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.CinemaEntity

@Dao
interface CinemaDao {
    @Query("SELECT * FROM cinema")
    fun findAll(): List<CinemaEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(cinemaEntities: List<CinemaEntity>)
}
