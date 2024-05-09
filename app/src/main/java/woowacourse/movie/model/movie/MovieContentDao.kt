package woowacourse.movie.model.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieContentDao {
    @Upsert
    fun save(data: MovieContentEntity): Long

    @Query("SELECT * FROM movie_content WHERE id=:id")
    fun find(id: Long): MovieContentEntity

    @Query("SELECT * FROM movie_content")
    fun findAll(): List<MovieContentEntity>

    @Query("DELETE FROM movie_content")
    fun deleteAll()
}
