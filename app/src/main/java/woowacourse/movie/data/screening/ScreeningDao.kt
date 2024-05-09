package woowacourse.movie.data.screening

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScreeningDao {
    @Insert
    fun insert(screeningDto: ScreeningDto)

    @Query("select * from screenings where id == :id")
    fun findById(id: Long): ScreeningDto

    @Query("select * from screenings where movieId == :movieId and theaterId == :theaterId")
    fun findByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<ScreeningDto>
}
