package woowacourse.movie.data.theater

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TheaterDao {
    @Insert
    fun insert(theaterDto: TheaterDto)

    @Query("select * from theaters where id == :id")
    fun findById(id: Long): TheaterDto?
}
