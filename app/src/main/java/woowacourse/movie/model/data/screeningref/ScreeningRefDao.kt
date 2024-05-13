package woowacourse.movie.model.data.screeningref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScreeningRefDao {
    @Insert
    fun insert(screeningRefDto: ScreeningRefDto)

    @Insert
    fun insertAll(screeningRefDtos: List<ScreeningRefDto>)

    @Query("select * from screenings where id == :id")
    fun findById(id: Long): ScreeningRefDto?

    @Query("select * from screenings where movieId == :movieId and theaterId == :theaterId")
    fun findByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<ScreeningRefDto>

    @Query("select * from screenings where movieId == :movieId")
    fun findByMovieId(movieId: Long): List<ScreeningRefDto>
}
