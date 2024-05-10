package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.MovieTheater

@Dao
interface MovieTheaterDao {
    @Query("SELECT * FROM movie_theater")
    fun getAll(): List<MovieTheater>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movieTheaters: MovieTheater)

    @Delete
    fun delete(theater: MovieTheater)

    @Query("SELECT * FROM movie_theater WHERE id = :theaterId")
    fun getMovieTheaterById(theaterId: Long): MovieTheater
}
