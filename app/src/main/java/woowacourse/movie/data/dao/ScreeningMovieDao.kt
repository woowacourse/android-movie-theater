package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.MovieTheaterEntity
import woowacourse.movie.data.entity.ScreeningMovieEntity

@Dao
interface ScreeningMovieDao {
    @Query("SELECT * FROM screening_movie")
    fun getAll(): List<ScreeningMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg screeningMovieEntities: ScreeningMovieEntity)

    @Delete
    fun delete(screeningMovieEntity: ScreeningMovieEntity)

    @Query("SELECT * FROM screening_movie WHERE id = :screenMovieId")
    fun getScreenMovieById(screenMovieId: Long): ScreeningMovieEntity

    @Query("SELECT * FROM screening_movie WHERE movie_id = :movieId AND theater_id = :theaterId")
    fun getByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): ScreeningMovieEntity

    @Query("SELECT theater_id AS id, theater_name AS name FROM screening_movie WHERE movie_id = :movieId")
    fun getTheatersByMovieId(movieId: Long): List<MovieTheaterEntity>
}
