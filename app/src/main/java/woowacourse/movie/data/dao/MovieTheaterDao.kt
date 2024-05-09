package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.MovieTheater

@Dao
interface MovieTheaterDao {
    @Query("SELECT * FROM movie_theater")
    fun getAll(): List<MovieTheater>

    @Insert
    fun insertAll(vararg movieTheaters: MovieTheater)

    @Delete
    fun delete(theater: MovieTheater)

    @Query("SELECT * FROM movie_theater WHERE id = :theaterId")
    fun getMovieById(theaterId: Long): MovieTheater
}
