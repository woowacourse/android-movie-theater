package woowacourse.movie.model.data.storage

import java.time.LocalTime

interface MovieTheaterStorage {

    fun getTheatersByMovieId(movieId: Long): List<String>

    fun getTheaterTimeTableByMovieId(movieId: Long, theater: String): List<LocalTime>
}
