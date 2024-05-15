package woowacourse.movie.data.database.theater

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TheaterDao {
    @Upsert
    fun save(data: TheaterEntity): Long

    @Query("SELECT * FROM theater WHERE id=:id")
    fun find(id: Long): TheaterEntity

    @Query("SELECT * FROM theater")
    fun findAll(): List<TheaterEntity>

    @Query("DELETE FROM theater")
    fun deleteAll()
}
