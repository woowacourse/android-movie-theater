package woowacourse.movie.model.movieInfo

import java.io.Serializable
import java.time.LocalDate

class MovieDate(private val date: LocalDate = LocalDate.of(1,1,1)) : Serializable {
    override fun toString(): String = "${date.year}.${date.monthValue}.${date.dayOfMonth}"
}
