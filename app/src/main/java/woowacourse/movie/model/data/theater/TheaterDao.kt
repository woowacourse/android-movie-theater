package woowacourse.movie.model.data.theater

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TheaterDao {
    @Insert
    fun insert(theaterDto: TheaterDto)

    @Query("select * from theaters where id == :id")
    fun findById(id: Long): TheaterDto?

    @Query("select * from theaters where id In (:idList)")
    fun findByIds(idList: List<Long>): List<TheaterDto>
}
