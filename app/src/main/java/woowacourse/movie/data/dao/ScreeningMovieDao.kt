package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.ScreeningMovie

@Dao
interface ScreeningMovieDao {
    @Query("SELECT * FROM screening_movie")
    fun getAll(): List<ScreeningMovie>

    @Insert
    fun insertAll(vararg screeningMovies: ScreeningMovie)

    @Delete
    fun delete(screeningMovie: ScreeningMovie)

    @Query("SELECT * FROM screening_movie WHERE id = :screenMovieId")
    fun getScreenMovieById(screenMovieId: Long): ScreeningMovie

    @Query("SELECT * FROM screening_movie WHERE movie_id = :movieId AND theater_id = :theaterId")
    fun getByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<ScreeningMovie>
}
