package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.MovieTheater

@Entity(tableName = "movie_theater")
data class MovieTheaterEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
) {
    fun toMovieTheater(): MovieTheater =
        MovieTheater(
            id,
            name,
            MovieTheater.seatSystem.seats(),
        )

    companion object {
        val STUB_A =
            MovieTheaterEntity(
                0L,
                "잠실 극장",
            )

        val STUB_B =
            MovieTheaterEntity(
                1L,
                "선릉 극장",
            )

        val STUB_C =
            MovieTheaterEntity(
                3L,
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
            )
    }
}
