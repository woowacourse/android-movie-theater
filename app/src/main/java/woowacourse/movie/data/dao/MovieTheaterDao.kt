package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.MovieTheaterEntity

@Dao
interface MovieTheaterDao {
    @Query("SELECT * FROM movie_theater")
    fun getAll(): List<MovieTheaterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movieTheaterEntities: MovieTheaterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieTheaterEntity: MovieTheaterEntity)

    @Delete
    fun delete(theater: MovieTheaterEntity)

    @Query("SELECT * FROM movie_theater WHERE id = :theaterId")
    fun getMovieTheaterById(theaterId: Long): MovieTheaterEntity
}
