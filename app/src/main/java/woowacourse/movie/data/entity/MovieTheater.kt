package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_theater")
data class MovieTheater(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
) {
    fun toMovieTheater(): woowacourse.movie.model.MovieTheater = woowacourse.movie.model.MovieTheater(id, name)

    companion object {
        val STUB_A =
            MovieTheater(
                0L,
                "잠실 극장",
            )

        val STUB_B =
            MovieTheater(
                1L,
                "선릉 극장",
            )

        val STUB_C =
            MovieTheater(
                2L,
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
            )
    }
}
